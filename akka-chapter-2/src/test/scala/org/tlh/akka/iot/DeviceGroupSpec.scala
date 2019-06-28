package org.tlh.akka.iot

import akka.actor.{ActorSystem, PoisonPill}
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.tlh.akka.iot.actors.DeviceGroup
import org.tlh.akka.iot.model._

/**
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class DeviceGroupSpec(_system: ActorSystem) extends TestKit(_system) with Matchers
  with WordSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("DeviceGroupSpec"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "DeviceGroup Testing" should {

    "return same actor for same deviceId" in {
      val probe = TestProbe()// 创建一个sender对象
      val groupActor = system.actorOf(DeviceGroup.props("group"))

      //使用sender对象向该actor发送消息
      groupActor.tell(RequestTrackDevice("group", "device1"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      // 获取最后发送消息的sender 及 device1
      val deviceActor1 = probe.lastSender

      groupActor.tell(RequestTrackDevice("group", "device1"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      val deviceActor2 = probe.lastSender

      deviceActor1 should ===(deviceActor2)
    }

    "be able to list active devices" in {
      val probe = TestProbe()
      val groupActor = system.actorOf(DeviceGroup.props("group"))

      // 注册两个 device
      groupActor.tell(RequestTrackDevice("group", "device1"), probe.ref)
      probe.expectMsg(DeviceRegistered)

      groupActor.tell(RequestTrackDevice("group", "device2"), probe.ref)
      probe.expectMsg(DeviceRegistered)

      groupActor.tell(RequestDeviceList(requestId = 0),probe.ref)
      probe.expectMsg(ReplyDeviceList(requestId = 0,Set("device1","device2")))
    }

    "be able to list active devices after one shuts down" in{
      val probe = TestProbe()
      val groupActor = system.actorOf(DeviceGroup.props("group"))

      // 注册两个 device
      groupActor.tell(RequestTrackDevice("group", "device1"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      val toShutDown=probe.lastSender

      groupActor.tell(RequestTrackDevice("group", "device2"), probe.ref)
      probe.expectMsg(DeviceRegistered)

      groupActor.tell(RequestDeviceList(requestId = 0),probe.ref)
      probe.expectMsg(ReplyDeviceList(requestId = 0,Set("device1","device2")))

      probe.watch(toShutDown)
      //发送 PoisonPill
      toShutDown ! PoisonPill
      probe.expectTerminated(toShutDown)

      // using awaitAssert to retry because it might take longer for the groupActor
      // to see the Terminated, that order is undefined
      probe.awaitAssert{
        groupActor.tell(RequestDeviceList(requestId = 1),probe.ref)
        probe.expectMsg(ReplyDeviceList(requestId = 1,Set("device2")))
      }
    }

    "be able to collect temperatures from all active devices" in {
      val probe = TestProbe()
      val groupActor = system.actorOf(DeviceGroup.props("group"))

      groupActor.tell(RequestTrackDevice("group", "device1"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      val deviceActor1 = probe.lastSender

      groupActor.tell(RequestTrackDevice("group", "device2"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      val deviceActor2 = probe.lastSender

      groupActor.tell(RequestTrackDevice("group", "device3"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      val deviceActor3 = probe.lastSender

      // Check that the device actors are working
      deviceActor1.tell(RecordTemperature(requestId = 0, 1.0), probe.ref)
      probe.expectMsg(TemperatureRecorded(requestId = 0))

      deviceActor2.tell(RecordTemperature(requestId = 1, 2.0), probe.ref)
      probe.expectMsg(TemperatureRecorded(requestId = 1))
      // No temperature for device3

      groupActor.tell(RequestAllTemperatures(requestId = 0), probe.ref)
      probe.expectMsg(
        RespondAllTemperatures(
          requestId = 0,
          temperatures = Map(
            "device1" -> Temperature(1.0),
            "device2" -> Temperature(2.0),
            "device3" -> TemperatureNotAvailable)))
    }

  }

}
