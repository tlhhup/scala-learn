package org.tlh.scala.collections

/**
  * Created by 离歌笑tlh/hu ping on 2019/6/29
  * <p>
  * Github: https://github.com/tlhhup
  */
object PartitionDemo {

  def main(args: Array[String]): Unit = {
    val numbers = Array(4, 5, 612, 3, 345, 565, 46123, 34345, 4)
    val tuple = numbers.partition(_ > 100)
    print(tuple._1+":"+tuple._2.toString)
  }

}

