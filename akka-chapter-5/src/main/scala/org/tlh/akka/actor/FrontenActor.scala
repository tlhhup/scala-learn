package org.tlh.akka.actor

import java.io.File

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, PoisonPill, Props, Terminated}
import akka.pattern.Patterns
import com.typesafe.config.ConfigFactory
import org.tlh.akka.model._

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.io.Source
import scala.util.Success
import scala.util.control.Breaks
import scala.util.control.Breaks.break

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
class FrontendActor extends Actor with ActorLogging {

  var id2Worker = Map.empty[String, ActorRef]

  var worker2Id = Map.empty[ActorRef, String]

  val workers = ListBuffer.empty[ActorRef]

  var counter: Int = 0

  override def receive: Receive = {

    case "isReady" => {
      val status = if (workers.isEmpty) "notReady" else "ready"
      sender() ! status
    }

    case RegisterMessage(id) => {
      id2Worker.get(id) match {
        case None => {
          log.info("receive worker:[{}] register message", id)

          val worker = sender()
          id2Worker += id -> worker
          worker2Id += worker -> id
          workers.append(worker)
          context.watch(worker)
        }
      }
    }

    case article: Article => {
      log.info("send word count request")
      counter += 1
      val index = counter % workers.size
      val worker = workers(index)
      worker.forward(article)
    }

    case Terminated(actorRef) => {
      context.unwatch(actorRef)

      val id = worker2Id(actorRef)
      log.info("worker:[{}] is downed", id)
      workers -= actorRef
      id2Worker -= id
      worker2Id -= actorRef
    }

    case PoisonPill => context.stop(self)
  }


}

object FrontendActor {

  import Breaks.breakable
  import scala.concurrent.duration._

  def main(args: Array[String]): Unit = {
    val port = args(0).toInt

    // 添加角色表示为前端actor
    val config = ConfigFactory.parseString(
      s"""
         |akka.remote.netty.tcp.port=$port
         |akka.cluster.roles=["Frontend"]
      """.stripMargin).withFallback(ConfigFactory.load())

    val system = ActorSystem("ClusterSystem", config)

    val frontendActor = system.actorOf(Props[FrontendActor], "FrontendActor")


    breakable {
      while (true) {
        // 查询worker是否已经就绪
        val future = Patterns.ask(frontendActor, "isReady", 1000)
        var result: Any = null
        try {
          result = Await.result(future, 1000 seconds)
        } catch {
          case e: Exception => e.printStackTrace()
        }
        if (result.isInstanceOf[String] && "ready".equals(result.asInstanceOf[String])) {
          println("worker is ready")
          break
        }
      }
    }

    import scala.concurrent.ExecutionContext.Implicits.global

    //发送请求
    val dir = args(1)
    val directory = new File(dir)
    val files = directory.listFiles()
    for (file <- files) {
      val content = Source.fromFile(file).mkString
      Patterns.ask(frontendActor, Article(file.getName, content), 10).onComplete {
        case Success(result) => println(result)
        case info@_ => println(info)
      }
    }
  }

}
