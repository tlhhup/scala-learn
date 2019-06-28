package org.tlh.akka.iot

import akka.actor.{ActorSystem, PoisonPill}
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.tlh.akka.iot.actors.DeviceGroupQuery
import org.tlh.akka.iot.model._

import scala.concurrent.duration._


/**
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
class DeviceGroupQuerySpec(_system: ActorSystem) extends TestKit(_system) with Matchers
  with WordSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("DeviceManagerSpec"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "DeviceGroupQuery Testing" should {
    "return temperature value for working devices" in {
      val requester = TestProbe()

      val device1 = TestProbe()
      val device2 = TestProbe()

      val queryActor = system.actorOf(
        DeviceGroupQuery.props(
          actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
          requestId = 1,
          requester = requester.ref,
          timeout = 20.seconds))

      //断言收到请求温度消息
      device1.expectMsg(ReadTemperature(requestId = 0))
      device2.expectMsg(ReadTemperature(requestId = 0))

      queryActor.tell(RespondTemperature(requestId = 0, Some(1.0)), device1.ref)
      queryActor.tell(RespondTemperature(requestId = 0, Some(2.0)), device2.ref)

      requester.expectMsg(30.seconds,
        RespondAllTemperatures(
          requestId = 1,
          temperatures = Map("device1" -> Temperature(1.0), "device2" -> Temperature(2.0))))
    }

  }

  it should {
    "return TemperatureNotAvailable for devices with no readings" in {
      val requester = TestProbe()

      val device1 = TestProbe()
      val device2 = TestProbe()

      val queryActor = system.actorOf(
        DeviceGroupQuery.props(
          actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
          requestId = 1,
          requester = requester.ref,
          timeout = 20.seconds))

      device1.expectMsg(ReadTemperature(requestId = 0))
      device2.expectMsg(ReadTemperature(requestId = 0))

      queryActor.tell(RespondTemperature(requestId = 0, None), device1.ref)
      queryActor.tell(RespondTemperature(requestId = 0, Some(2.0)), device2.ref)

      requester.expectMsg(30.seconds,
        RespondAllTemperatures(
          requestId = 1,
          temperatures =
            Map("device1" -> TemperatureNotAvailable, "device2" -> Temperature(2.0))))
    }
  }

  it should {
    "return DeviceNotAvailable if device stops before answering" in {
      val requester = TestProbe()

      val device1 = TestProbe()
      val device2 = TestProbe()

      val queryActor = system.actorOf(
        DeviceGroupQuery.props(
          actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
          requestId = 1,
          requester = requester.ref,
          timeout = 3.seconds))

      device1.expectMsg(ReadTemperature(requestId = 0))
      device2.expectMsg(ReadTemperature(requestId = 0))

      queryActor.tell(RespondTemperature(requestId = 0, Some(1.0)), device1.ref)
      device2.ref ! PoisonPill

      requester.expectMsg(
        RespondAllTemperatures(
          requestId = 1,
          temperatures = Map("device1" -> Temperature(1.0), "device2" -> DeviceNotAvailable)))
    }
  }

  it should {
    "return temperature reading even if device stops after answering" in {
      val requester = TestProbe()

      val device1 = TestProbe()
      val device2 = TestProbe()

      val queryActor = system.actorOf(
        DeviceGroupQuery.props(
          actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
          requestId = 1,
          requester = requester.ref,
          timeout = 3.seconds))

      device1.expectMsg(ReadTemperature(requestId = 0))
      device2.expectMsg(ReadTemperature(requestId = 0))

      queryActor.tell(RespondTemperature(requestId = 0, Some(1.0)), device1.ref)
      queryActor.tell(RespondTemperature(requestId = 0, Some(2.0)), device2.ref)
      device2.ref ! PoisonPill

      requester.expectMsg(
        RespondAllTemperatures(
          requestId = 1,
          temperatures = Map("device1" -> Temperature(1.0), "device2" -> Temperature(2.0))))
    }
  }

  it should {
    "return DeviceTimedOut if device does not answer in time" in {
      val requester = TestProbe()

      val device1 = TestProbe()
      val device2 = TestProbe()

      val queryActor = system.actorOf(
        DeviceGroupQuery.props(
          actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
          requestId = 1,
          requester = requester.ref,
          timeout = 1.second))

      device1.expectMsg(ReadTemperature(requestId = 0))
      device2.expectMsg(ReadTemperature(requestId = 0))

      queryActor.tell(RespondTemperature(requestId = 0, Some(1.0)), device1.ref)

      requester.expectMsg(
        RespondAllTemperatures(
          requestId = 1,
          temperatures = Map("device1" -> Temperature(1.0), "device2" -> DeviceTimedOut)))
    }
  }

}
