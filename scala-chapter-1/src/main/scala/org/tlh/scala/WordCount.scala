package org.tlh.scala

/**
  * Created by hu ping on 6/25/2019
  * <p>
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    val words = Array("hello world how are you i am fine", "who are you hello")
    val result = words.flatMap(_.split(" "))
      .map(WC(_, 1))
      .groupBy(_.word)
      .mapValues(_.size)
      .toList
      .sortBy(_._2)
    println(result)
  }

  case class WC(word: String, count: Int)

}
