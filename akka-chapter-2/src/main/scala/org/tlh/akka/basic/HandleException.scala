package org.tlh.akka.basic

import akka.actor.{Actor, ActorSystem, Props}

/**
  * 异常监视
  * Created by hu ping on 6/26/2019
  * <p>
  */
object HandleException {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("handleException")
    val superviser = actorSystem.actorOf(SupervisingActor.props,"parent")

    superviser ! "failChild"
  }

}


object SupervisingActor {
  def props: Props = Props(new SupervisingActor)
}

class SupervisingActor extends Actor {
  val child = context.actorOf(SupervisedActor.props, "supervised-actor")

  override def receive: Receive = {
    case "failChild" => child ! "fail"
  }
}

object SupervisedActor {
  def props: Props =
    Props(new SupervisedActor)
}

class SupervisedActor extends Actor {
  override def preStart(): Unit = println("supervised actor started")

  override def postStop(): Unit = println("supervised actor stopped")

  override def receive: Receive = {
    case "fail" =>
      println("supervised actor fails now")
      throw new Exception("I failed!")
  }
}