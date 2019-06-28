package org.tlh.akka.basic

import akka.actor
import akka.actor.{Actor, ActorSystem, Props}

import scala.io.StdIn

/**
  * actor的结构，可以理解为url地址<br>
  *   1. root 为：/ <br>
  *   2. user 为：/user <br>
  *   3. system为：/system<br>
  * <br>
  * Created by hu ping on 6/26/2019
  * <p>
  */
object ActorHierarchyExperiments extends App {
  //构建根对象，如果在远程通信时设置为主机名称
  val system = ActorSystem("testSystem") // 返回一个actorRef 实质也是一个url地址

  val firstRef = system.actorOf(PrintMyActorRefActor.props, "first-actor")
  println(s"First: $firstRef")
  firstRef ! "printit"

  println(">>> Press ENTER to exit <<<")
  try StdIn.readLine()
  finally system.terminate()
}

object PrintMyActorRefActor {
  def props: Props =
    actor.Props(new PrintMyActorRefActor)
}

class PrintMyActorRefActor extends Actor {
  override def receive: Receive = {
    case "printit" =>
      val secondRef = context.actorOf(Props.empty, "second-actor")
      println(s"Second: $secondRef")
  }
}
