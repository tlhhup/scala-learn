package org.tlh.akka.rpc

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import model._

/**
  * Created by 离歌笑tlh/hu ping on 2019/6/30
  * <p>
  * Github: https://github.com/tlhhup
  */
class Worker(masterHost: String, masterPort: Int, memory: Long, cores: Int) extends Actor with ActorLogging {

  var masterSelection: ActorSelection = _

  val workerId = UUID.randomUUID().toString

  //超时检查的间隔
  val HEARTBEAT_INTERVAL = 15000

  override def preStart(): Unit = {
    log.info("Worker: {} send register message to Master:{}", workerId, masterHost)
    masterSelection = context.actorSelection(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/Master")
    //发送注册消息
    masterSelection ! RegisterWorker(workerId, memory, cores)
  }

  override def postStop(): Unit = {
    log.info("Worker:{} stop", workerId)
  }

  import context.dispatcher
  import scala.concurrent.duration._

  override def receive: Receive = {

    case WorkRegistered => {
      log.info("received registered message")
      context.system.scheduler.schedule(0 millis, HEARTBEAT_INTERVAL millis, self, SendHeartbeat)
    }

    case SendHeartbeat => {
      log.info("Worker send heartbeat message to {}", masterHost)
      masterSelection ! HeartBeat(workerId)
    }
  }
}

object Worker {

  def main(args: Array[String]): Unit = {
    val masterHost = args(0)
    val masterPort = args(1).toInt

    val host = args(2)
    val port = args(3).toInt
    val memory = args(4).toLong
    val cores = args(5).toInt

    // 准备配置
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = $port
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    //ActorSystem老大，辅助创建和监控下面的Actor，他是单例的
    val actorSystem = ActorSystem("WorkerSystem", config)
    actorSystem.actorOf(Props(new Worker(masterHost, masterPort, memory, cores)), "Worker")
  }

}
