package org.tlh.scala.bound

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
object Test {

  def main(args: Array[String]): Unit = {
    val a = new Girl(10, "a")
    val b = new Girl(12, "b")

    import MyPredef._

    var girl = Chooser.select1(a, b)
    println(girl.name)

    girl = new ContentBound[Girl].bigger(a, b)
    println(girl.name)
  }

}
