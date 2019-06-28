package org.tlh.akka.iot

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.tlh.akka.iot.actors.Device
import org.tlh.akka.iot.model._

import scala.concurrent.duration._

/**
  * Created by hu ping on 6/26/2019
  * <p>
  */
class DeviceSpec(_system: ActorSystem) extends TestKit(_system)
  with Matchers
  with WordSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("DeviceSpec"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "Device testing" should {

    "reply with empty reading if no temperature is known" in {
      val probe = TestProbe()
      val deviceActor = system.actorOf(Device.props("group", "device"))

      deviceActor.tell(ReadTemperature(requestId = 42), probe.ref)
      val response = probe.expectMsgType[RespondTemperature]
      response.requestId should ===(42L)
      response.value should ===(None)
    }

    "reply with latest temperature reading" in {
      val probe = TestProbe()
      val deviceActor = system.actorOf(Device.props("group", "device"))

      //更新温度值
      deviceActor.tell(RecordTemperature(requestId = 1, 24.0), probe.ref)
      probe.expectMsg(TemperatureRecorded(requestId = 1))

      //读取温度值
      deviceActor.tell(ReadTemperature(requestId = 2), probe.ref)
      val response1 = probe.expectMsgType[RespondTemperature]
      //使用matcher
      response1.requestId should ===(2L)
      response1.value should ===(Some(24.0))

      deviceActor.tell(RecordTemperature(requestId = 3, 55.0), probe.ref)
      probe.expectMsg(TemperatureRecorded(requestId = 3))

      deviceActor.tell(ReadTemperature(requestId = 4), probe.ref)
      val response2 = probe.expectMsgType[RespondTemperature]

      response2.requestId should ===(4L)
      response2.value should ===(Some(55.0))
    }


    "reply to registration requests" in {
      val probe = TestProbe()
      val deviceActor = system.actorOf(Device.props("group", "device"))

      deviceActor.tell(RequestTrackDevice("group", "device"), probe.ref)
      probe.expectMsg(DeviceRegistered)
      probe.lastSender should ===(deviceActor)
    }

    "ignore wrong registration requests" in {
      val probe = TestProbe()
      val deviceActor = system.actorOf(Device.props("group", "device"))

      deviceActor.tell(RequestTrackDevice("wrongGroup", "device"), probe.ref)
      probe.expectNoMessage(500.milliseconds)

      deviceActor.tell(RequestTrackDevice("group", "Wrongdevice"), probe.ref)
      probe.expectNoMessage(500.milliseconds)
    }

  }

}
