package org.tlh.scala.io

import scala.io.Source

/**
  * Created by 离歌笑tlh/hu ping on 2019/6/30
  * <p>
  * Github: https://github.com/tlhhup
  */
object ReadFromFile {

  def main(args: Array[String]): Unit = {
    val filePath = "/Users/huping/IdeaProjects/scala-learn/akka-chapter-3/docs/a.txt"
    val file = Source.fromFile(filePath)
    val content=file.mkString
    println(content)
  }

}
