package org.tlh.scala

import akka.actor.{Actor, ActorSystem, PoisonPill}

import scala.io.Source

/**
  * Created by 离歌笑tlh/hu ping on 2019/6/30
  * <p>
  * Github: https://github.com/tlhhup
  */
class AkkaWordCount extends Actor {

  override def receive: Receive = {

    case SubmitTask(filePath) => {
      val file = Source.fromFile(filePath)
      val lines = file.getLines()
      val result = lines
        .flatMap(_.split(" "))
        .map((_, 1))
        .toList
        .groupBy(_._1)
        .mapValues(_.size)

      result
    }

    case PoisonPill => context.stop(self)
  }
}

object AkkaWordCount {

  def main(args: Array[String]): Unit = {
    /*val filePath = Array(
      "/Users/huping/IdeaProjects/scala-learn/akka-chapter-3/docs/a.txt",
      "/Users/huping/IdeaProjects/scala-learn/akka-chapter-3/docs/a.txt")

    val wordCount = ActorSystem("wordCount")
    filePath.foreach(file => {
    })*/


    val lines = Source.fromFile("/Users/huping/IdeaProjects/scala-learn/akka-chapter-3/docs/a.txt").getLines()
    val result = lines.flatMap(_.split(" "))
      .map((_, 1))
      .toList
      .groupBy(_._1)
      .mapValues(_.size)
    println(result)
  }

}

case class SubmitTask(filePath: String)