package org.tlh.akka.iot

import akka.actor.ActorSystem
import org.tlh.akka.iot.actors.IotSupervisor

import scala.io.StdIn

/**
  * Created by hu ping on 6/26/2019
  * <p>
  */
object IOTApplication {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("iot-system")
    try {
      val supervisor = system.actorOf(IotSupervisor.pros, "iot-supervisor")


      StdIn.readLine()
    } finally {
      system.terminate()
    }
  }

}
