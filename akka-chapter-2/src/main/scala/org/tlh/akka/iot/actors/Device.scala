package org.tlh.akka.iot.actors

import akka.actor.{Actor, ActorLogging, Props}

/**
  * Created by hu ping on 6/26/2019
  * <p>
  */
class Device(groupId: String, deviceId: String) extends Actor with ActorLogging {

  import org.tlh.akka.iot.model._

  var lastTemperatureReading: Option[Double] = None


  override def preStart(): Unit = log.info("Device actor {}-{} started", groupId, deviceId)

  override def postStop(): Unit = log.info("Device actor {}-{} stopped", groupId, deviceId)

  override def receive: Receive = {
    //  变量匹配： 只有当该位置值和引用的变量的值相等时，它才会匹配  和当前的属性值进行比较
    case RequestTrackDevice(`groupId`, `deviceId`) => {
      sender() ! DeviceRegistered
    }

    case RequestTrackDevice(groupId, deviceId) => {
      log.warning(
        "Ignoring TrackDevice request for {}-{}.This actor is responsible for {}-{}.",
        groupId,
        deviceId,
        this.groupId,
        this.deviceId)
    }

    case RecordTemperature(requestId, value) => {
      log.info("Record temperature reading {} with {}", value, requestId)
      this.lastTemperatureReading = Some(value)
      sender() ! TemperatureRecorded(requestId)
    }

    case ReadTemperature(requestId) => {
      sender() ! RespondTemperature(requestId, lastTemperatureReading)
    }
  }
}

object Device {
  def props(groupId: String, deviceId: String): Props = Props(new Device(groupId, deviceId))
}
