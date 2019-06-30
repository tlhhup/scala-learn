package org.tlh.akka.rpc

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import model._

import scala.collection.mutable
import scala.concurrent.duration._

/**
  * Created by 离歌笑tlh/hu ping on 2019/6/30
  * <p>
  * Github: https://github.com/tlhhup
  */
class Master(host: String, port: Int) extends Actor with ActorLogging {

  val id2Worker = new mutable.HashMap[String, WorkerInfo]()
  val workers = new mutable.HashSet[WorkerInfo]()

  //超时检查的间隔
  val CHECK_INTERVAL = 15000

  import context.dispatcher


  override def preStart(): Unit = {
    log.info("Master run on host:{} port:{}", host, port)
    //启动worker检查
    context.system.scheduler.scheduleAtFixedRate(0 millis, CHECK_INTERVAL milli, self, CheckTimeOutWorker)
  }

  override def receive: Receive = {
    case RegisterWorker(id, memory, cores) => {
      id2Worker.get(id) match {
        case None => {
          val workerInfo = new WorkerInfo(id, memory, cores)
          id2Worker += id -> workerInfo
          workers += workerInfo

          //发送响应
          sender() ! WorkRegistered
        }
      }
    }

    case HeartBeat(id) => {
      id2Worker.get(id) match {
        case Some(workerInfo) => {
          //更新时间
          val currentTime = System.currentTimeMillis()
          workerInfo.lastHeartbeatTime = currentTime
        }
      }
    }

    case CheckTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val timeOutWorkers = workers.filter(x => currentTime - x.lastHeartbeatTime > CHECK_INTERVAL)
      val timeOutWorkerIds = timeOutWorkers.map(_.id)
      //移除
      workers --= timeOutWorkers
      id2Worker --= timeOutWorkerIds

      println(id2Worker)
    }
  }

}

object Master {

  def main(args: Array[String]): Unit = {
    val host = args(0)
    val port = args(1).toInt
    // 准备配置
    val configStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.enabled-transports = ["akka.remote.netty.tcp"]
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = $port
       """.stripMargin
    val config = ConfigFactory.parseString(configStr)
    val masterSystem = ActorSystem("MasterSystem", config)
    //启动master节点
    masterSystem.actorOf(Props(new Master(host, port)), "Master")
  }

}
