package org.tlh.scala

/**
  * <br>
  * Created by hu ping on 6/27/2019
  * <p>
  */
object PatternMatch extends App {

  //变量模式
  val v42 = 42
  Some(3) match {
    case a@Some(`v42`) => {
      println("42") //不用反引号，理解为变量，一直输出42, 引用前面定义的变量
      println(a)
    }
    case _ => println("Not 42") //输出
  }

}
