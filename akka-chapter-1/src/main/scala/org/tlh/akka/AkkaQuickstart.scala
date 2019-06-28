package org.tlh.akka

import akka.actor.{ActorRef, ActorSystem}
import org.tlh.akka.Greeter.{Greet, WhoToGreet}

/**
  * Created by hu ping on 6/25/2019
  * <p>
  */
object AkkaQuickstart {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("helloAkka")

    // Create the printer actor
    val printer: ActorRef = system.actorOf(Printer.props, "printerActor")

    // Create the 'greeter' actors
    val howdyGreeter: ActorRef =
      system.actorOf(Greeter.props("Howdy", printer), "howdyGreeter")
    val helloGreeter: ActorRef =
      system.actorOf(Greeter.props("Hello", printer), "helloGreeter")
    val goodDayGreeter: ActorRef =
      system.actorOf(Greeter.props("Good day", printer), "goodDayGreeter")


    // send message
    howdyGreeter ! WhoToGreet("Akka")
    howdyGreeter ! Greet

    howdyGreeter ! WhoToGreet("Lightbend")
    howdyGreeter ! Greet

    helloGreeter ! WhoToGreet("Scala")
    helloGreeter ! Greet

    goodDayGreeter ! WhoToGreet("Play")
    goodDayGreeter ! Greet
  }

}
