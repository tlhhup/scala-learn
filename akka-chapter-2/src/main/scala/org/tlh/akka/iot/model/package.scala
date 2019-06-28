package org.tlh.akka.iot

/**
  * Created by hu ping on 6/26/2019
  * <p>
  */
package object model {

  /**
    * 查询温度请求
    */
  final case class ReadTemperature(requestId: Long)

  /**
    * 返回当前温度
    *
    * @param requestId
    * @param value
    */
  final case class RespondTemperature(requestId: Long, value: Option[Double])

  /**
    * 请求记录温度值
    *
    * @param requestId
    * @param value
    */
  final case class RecordTemperature(requestId: Long, value: Double)

  /**
    * 响应
    *
    * @param requestId
    */
  final case class TemperatureRecorded(requestId: Long)

  /**
    * device注册
    *
    * @param groupId
    * @param deviceId
    */
  final case class RequestTrackDevice(groupId: String, deviceId: String)

  /**
    * device注册响应
    */
  case object DeviceRegistered

  /**
    * 查询当前在线的device
    *
    * @param requestId
    */
  final case class RequestDeviceList(requestId: Long)

  final case class ReplyDeviceList(requestId: Long, ids: Set[String])


  final case class RequestAllTemperatures(requestId: Long)

  final case class RespondAllTemperatures(requestId: Long, temperatures: Map[String, TemperatureReading])

  final case class RequestGroupTemperatures(requestId: Long, groupId: String)

  // 使用sealed修饰的只能在当前文件中使用，不能在类定义的文件之外定义任何新的子类
  sealed trait TemperatureReading

  final case class Temperature(value: Double) extends TemperatureReading

  case object TemperatureNotAvailable extends TemperatureReading

  case object DeviceNotAvailable extends TemperatureReading

  case object DeviceTimedOut extends TemperatureReading

  //查询超时
  case object CollectionTimeout

}
