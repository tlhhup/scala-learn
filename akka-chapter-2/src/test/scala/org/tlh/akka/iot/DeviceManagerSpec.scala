package org.tlh.akka.iot

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.tlh.akka.iot.actors.DeviceManager
import org.tlh.akka.iot.model._

/**
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
class DeviceManagerSpec(_system: ActorSystem) extends TestKit(_system) with Matchers
  with WordSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("DeviceManagerSpec"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "DeviceManager Testing" should {

    "return same group with same groupId" in {
      val probe = TestProbe()
      val deviceManagerActor = system.actorOf(DeviceManager.props)

      //register device group
      deviceManagerActor.tell(RequestTrackDevice("group1", "device1"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      val group1 = probe.lastSender

      deviceManagerActor.tell(RequestTrackDevice("group1", "device1"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      val group2 = probe.lastSender

      group1 should ===(group2)
    }

    "return temperature by groupID" in {
      val probe = TestProbe()

      val deviceManagerActor = system.actorOf(DeviceManager.props)

      //register device group
      deviceManagerActor.tell(RequestTrackDevice("group1", "device1"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      val device1=probe.lastSender

      deviceManagerActor.tell(RequestTrackDevice("group1", "device2"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      val device2=probe.lastSender

      //存储数据
      device1.tell(RecordTemperature(requestId = 0, 1.0), probe.ref)
      probe.expectMsg(TemperatureRecorded(requestId = 0))

      device2.tell(RecordTemperature(requestId = 1, 2.0), probe.ref)
      probe.expectMsg(TemperatureRecorded(requestId = 1))

      //发送读取group1的数据
      deviceManagerActor.tell(RequestGroupTemperatures(requestId = 3, groupId = "group1"), probe.ref)
      probe.expectMsg(
        RespondAllTemperatures(
          requestId = 3,
          temperatures = Map(
            "device1" -> Temperature(1.0),
            "device2" -> Temperature(2.0)
          )
        )
      )
    }

  }

}
