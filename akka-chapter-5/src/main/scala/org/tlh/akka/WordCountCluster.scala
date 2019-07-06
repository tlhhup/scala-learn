package org.tlh.akka

import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import akka.cluster.metrics.{ClusterMetricsChanged, ClusterMetricsExtension, StandardMetrics}
import com.typesafe.config.ConfigFactory

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
class WordCountCluster extends Actor with ActorLogging {

  val cluster = Cluster(context.system)

  val clusterMetricsExtension = ClusterMetricsExtension(context.system)


  override def preStart(): Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents, classOf[UnreachableMember], classOf[MemberEvent])
    clusterMetricsExtension.subscribe(self)
  }


  override def postStop(): Unit = {
    cluster.unsubscribe(self)
    clusterMetricsExtension.unsubscribe(self)
  }

  override def receive: Receive = {
    case MemberJoined(member) =>
      log.info("Member is Joined: {}", member.address)

    case MemberUp(member) =>
      log.info("Member is Up: {}", member.address)

    case UnreachableMember(member) =>
      log.info("Member detected as unreachable: {}", member)

    case MemberRemoved(member, previousStatus) =>
      log.info("Member is Removed: {} after {}", member.address, previousStatus)

    case _: MemberEvent => // ignore

    case ClusterMetricsChanged(clusterMetrics) => {
      for (mestric <- clusterMetrics) {
        val builder = new StringBuilder
        builder ++= s"${mestric.address}的系统信息为："
        Option(StandardMetrics.extractHeapMemory(mestric)) match {
          case Some(heapMemory) => builder ++= s"Head info is ${heapMemory.used / (1024 * 1024.0)} M"
        }
        val cpu = StandardMetrics.extractCpu(mestric)
        if (cpu != null && cpu.systemLoadAverage.isDefined) {
          builder ++= s"Load info is ${cpu.systemLoadAverage.get}"
        }
      }
    }

    case PoisonPill => context.stop(self)
  }
}

object WordCountCluster {

  def main(args: Array[String]): Unit = {
    val port = 2555

    val config = ConfigFactory.parseString(
      s"""
         |akka.remote.netty.tcp.port=$port
      """.stripMargin).withFallback(ConfigFactory.load())

    val system = ActorSystem("ClusterSystem", config)

    system.actorOf(Props[WordCountCluster],"cluster")
  }

}
