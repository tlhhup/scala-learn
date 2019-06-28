package org.tlh.scala.constructor

/**
  * 对象的apply方法，必须定义中对象中，相对于构造函数，用于new class对象
  * Created by hu ping on 6/26/2019
  * <p>
  */
class ApplyDemo{

}

object ApplyDemo {

  def apply(): ApplyDemo = {
    println("不带参数的 apply方法")
    new ApplyDemo()
  }

  def apply(name: String): ApplyDemo = {
    println(s"带参数的 apply方法 $name")
    new ApplyDemo()
  }

  def main(args: Array[String]): Unit = {
    val apply1 = ApplyDemo("tlh")
    val apply2 = ApplyDemo()
  }

}
