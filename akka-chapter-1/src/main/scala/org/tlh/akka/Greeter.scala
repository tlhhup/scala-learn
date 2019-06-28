package org.tlh.akka

import akka.actor.{Actor, ActorRef, Props}

/**
  * Created by hu ping on 6/25/2019
  * <p>
  */
class Greeter(message: String, printerActor: ActorRef) extends Actor{

  import Greeter._
  import Printer._

  var greeting = "" // 状态变量

  def receive = {
    // 偏函数
    case WhoToGreet(who) =>
      greeting = message + ", " + who
    case Greet =>
      printerActor ! Greeting(greeting)
  }

}

object Greeter {
  def props(message: String, printerActor: ActorRef): Props = Props(new Greeter(message, printerActor))

  final case class WhoToGreet(who: String)

  case object Greet

}
