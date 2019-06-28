package org.tlh.akka.iot.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}
import scala.concurrent.duration._

/**
  * 负责管理所有的device
  * 1.如果device存在，则转发到该device
  * 2.如果device不存在，则进行创建
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class DeviceGroup(groupId: String) extends Actor with ActorLogging {

  var deviceIdToActor = Map.empty[String, ActorRef]

  var actorToDeviceId = Map.empty[ActorRef, String]

  override def preStart(): Unit = log.info("DeviceGroup {} started", groupId)

  override def postStop(): Unit = log.info("DeviceGroup {} stopped", groupId)

  import org.tlh.akka.iot.model._

  override def receive: Receive = {
    case trackMsg@RequestTrackDevice(`groupId`, _) => { // 通过 使用 @ 将匹配的对象绑定到变量上
      deviceIdToActor.get(trackMsg.deviceId) match {
        case Some(deviceActor) => {
          // 将请求转发，sender保持和原来一致
          deviceActor.forward(trackMsg)
        }

        case None => {
          log.info("Creating device actor for {}", trackMsg.deviceId)
          //创建device对象
          val deviceActor = context.actorOf(Device.props(groupId, trackMsg.deviceId), s"device-${trackMsg.deviceId}")
          //存储device对象
          deviceIdToActor += trackMsg.deviceId -> deviceActor
          actorToDeviceId += deviceActor -> trackMsg.deviceId
          //转发请求
          deviceActor.forward(trackMsg)

          // 开启监视
          context.watch(deviceActor)
        }
      }
    }

    case RequestTrackDevice(groupId, deviceId) => {
      log.warning("Ignoring TrackDevice request for {}. This actor is responsible for {}.", groupId, this.groupId)
    }

    case RequestDeviceList(requestId) => {
      sender() ! ReplyDeviceList(requestId, deviceIdToActor.keySet)
    }

    case RequestAllTemperatures(requestId)=>{
      context.actorOf(DeviceGroupQuery.props(actorToDeviceId,requestId,sender(),3.seconds))
    }

    case Terminated(deviceActor) => {
      val deviceId = actorToDeviceId(deviceActor)
      log.info("Device actor for {} has been terminated", deviceId)
      actorToDeviceId -= deviceActor
      deviceIdToActor -= deviceId
    }
  }

}

object DeviceGroup {

  def props(groupId: String): Props = Props(new DeviceGroup(groupId))

}
