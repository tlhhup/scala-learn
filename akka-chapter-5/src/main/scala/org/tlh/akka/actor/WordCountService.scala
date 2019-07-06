package org.tlh.akka.actor

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.{InitialStateAsEvents, MemberUp}
import com.typesafe.config.ConfigFactory
import org.tlh.akka.model._

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
class WordCountService extends Actor with ActorLogging {

  val id = UUID.randomUUID().toString

  val cluster = Cluster(context.system)

  override def preStart(): Unit = cluster.subscribe(self, initialStateMode = InitialStateAsEvents, classOf[MemberUp])

  override def postStop(): Unit = cluster.unsubscribe(self)

  override def receive: Receive = {

    case MemberUp(member) => {
      //如果是前端actor，则发送注册信息
      if (member.roles.contains("Frontend")) {
        val frontend = context.actorSelection(member.address + "/user/FrontendActor")
        frontend ! RegisterMessage(id)
      }
    }

    case Article(id, content) => {
      val size = content.split(" ").size
      sender() ! CountResult(id, size)
    }


    case PoisonPill => context.stop(self)

  }
}

object WordCountService{

  def main(args: Array[String]): Unit = {
    val port = args(0).toInt

    // 添加角色表示为前端actor
    val config = ConfigFactory.parseString(
      s"""
         |akka.remote.netty.tcp.port=$port
         |akka.cluster.roles=["Frontend"]
      """.stripMargin).withFallback(ConfigFactory.load())

    val system = ActorSystem("ClusterSystem", config)

    system.actorOf(Props[WordCountService], "WordCountService")
  }

}
