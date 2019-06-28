package org.tlh.akka

import akka.actor.{Actor, ActorLogging, Props}

/**
  * Created by hu ping on 6/25/2019
  * <p>
  */
class Printer extends Actor with ActorLogging {

  import Printer._

  override def receive: Receive = {
    case Greeting(greeting) => {
      log.info("Greeting received (from " + sender() + "): " + greeting)
    }
  }

}

// 伴生对象
object Printer {
  def props: Props = Props[Printer]

  //该actor期望处理的消息类型
  final case class Greeting(greeting: String)

}
