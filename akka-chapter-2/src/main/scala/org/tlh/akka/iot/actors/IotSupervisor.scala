package org.tlh.akka.iot.actors

import akka.actor.{Actor, ActorLogging, PoisonPill, Props}

/**
  * Created by hu ping on 6/26/2019
  * <p>
  */
class IotSupervisor extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("IOT Application started")

  override def postStop(): Unit = log.info("IOT Application stopped")

  override def receive: Receive = {
    case PoisonPill => context.stop(self)//停止
  }
}

object IotSupervisor {
  def pros: Props = Props[IotSupervisor]
}
