package org.tlh.akka.iot.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}

import scala.concurrent.duration.FiniteDuration

/**
  * 用于处理每次查询,每次查询新建一个actor，并传递当前所有devices和发送请求的原始对象
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
class DeviceGroupQuery(
                        requestId: Long,
                        requester: ActorRef,//发送请求的原始对象，用于请求处理完成之后，投递响应
                        actorToDeviceId: Map[ActorRef, String],//当前可用的所有device
                        timeOut: FiniteDuration
                      ) extends Actor with ActorLogging {

  import org.tlh.akka.iot.model._
  import context.dispatcher

  // 在timeout的时间给指定的actor发送指定的消息
  val queryTimeOutTimer = context.system.scheduler.scheduleOnce(timeOut, self, CollectionTimeout)


  override def preStart(): Unit = {
    //监听所有的device是否已经stop
    actorToDeviceId.keysIterator.foreach { deviceActor =>
      context.watch(deviceActor)
      //请求读取数据
      deviceActor ! ReadTemperature(0)
    }
  }


  override def postStop(): Unit = {
    queryTimeOutTimer.cancel()
  }

  // 实质是Receive在进行处理-->本质上就是一个偏函数
  //it is not receive that handles the messages, it returns a Receive function that will actually handle the messages
  override def receive: Receive = waitingForReplies(Map.empty, actorToDeviceId.keySet)

  def waitingForReplies(
                         repliesSoFar: Map[String, TemperatureReading],
                         stillWaiting: Set[ActorRef]
                       ): Receive = {
    case RespondTemperature(0, valueOption) => {
      //存储返回数据的device
      val deviceActor = sender()
      val reading = valueOption match {
        case Some(value) => Temperature(value)
        case None => TemperatureNotAvailable
      }
      receivedResponse(deviceActor, reading, repliesSoFar, stillWaiting)
    }

    case Terminated(deviceActor) => {
      receivedResponse(deviceActor, DeviceNotAvailable, repliesSoFar, stillWaiting)
    }

    case CollectionTimeout => {
      //将所有等待响应的device映射为timeout
      val timedOutReplies = stillWaiting.map { deviceActor =>
        val deviceId = actorToDeviceId(deviceActor)
        deviceId -> DeviceTimedOut
      }
      //响应
      requester ! RespondAllTemperatures(requestId, repliesSoFar ++ timedOutReplies)
      context.stop(self)
    }

  }

  def receivedResponse(
                        deviceActor: ActorRef, //响应的device
                        reading: TemperatureReading,
                        repliesSoFar: Map[String, TemperatureReading],
                        stillWaiting: Set[ActorRef]
                      ) = {
    //一旦有响应就移除监听
    context.unwatch(deviceActor)
    //获取deviceId
    val deviceId = actorToDeviceId(deviceActor)
    //存储响应的device
    val newRepliesSoFar = repliesSoFar + (deviceId -> reading)
    //等待中移除
    val newStillWaiting = stillWaiting - deviceActor
    if (newStillWaiting.isEmpty) {
      log.info("All device temperature read")
      requester ! RespondAllTemperatures(requestId, newRepliesSoFar)

      context.stop(self)
    } else {
      // 产生一个新的Receive
      context.become(waitingForReplies(newRepliesSoFar, newStillWaiting))
    }
  }

}

object DeviceGroupQuery {

  def props(
             actorToDeviceId: Map[ActorRef, String],
             requestId: Long,
             requester: ActorRef,
             timeout: FiniteDuration): Props = {
    Props(new DeviceGroupQuery(requestId, requester, actorToDeviceId, timeout))
  }
}