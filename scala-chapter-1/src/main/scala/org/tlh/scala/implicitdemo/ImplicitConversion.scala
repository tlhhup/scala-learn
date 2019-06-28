package org.tlh.scala.implicitdemo

/**
  * 隐式转换，查看当前上下文中的[[方法]]，并调用该方法实现  A => B 的转换 <br>
  * Created by hu ping on 6/26/2019
  * <p>
  */
object ImplicitConversion extends App {

  // 定义打印操作Trait
  trait PrintOps {
    val value: String

    def printWithSeperator(sep: String): Unit = {
      println(value.split("").mkString(sep))
    }
  }

  // 定义针对String的隐式转换方法
  implicit def stringToPrintOps(str: String): PrintOps = new PrintOps {
    override val value: String = str
  }

  // 定义针对Int的隐式转换方法
  implicit def intToPrintOps(i: Int): PrintOps = new PrintOps {
    override val value: String = i.toString
  }

  // String 和 Int 都拥有 printWithSeperator 函数
  "hello,world" printWithSeperator "*"
  1234 printWithSeperator "*"

}
