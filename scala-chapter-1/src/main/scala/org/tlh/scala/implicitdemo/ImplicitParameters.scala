package org.tlh.scala.implicitdemo

/**
  * 隐式参数转换 , 在当前上下文中查找该类型的[[变量]](有且只能有一个) <br>
  * Created by hu ping on 6/26/2019
  * <p>
  */
object ImplicitParameters extends App {

  //这是我们的全局配置类
  class Setting(config: String) {
    def host: String = config
  }

  // 申明一个隐式的配置对象
  implicit val setting = new Setting("hello")

  implicit val name = "hello"

  // 申明隐式参数
  def startServer()(implicit setting: Setting, name: String): Unit = {
    val host = setting.host
    println(s"server listening on $host $name")
  }

  // 无需传入隐式参数,从当前上下文中自动寻找该类型的变量
  startServer()

}
