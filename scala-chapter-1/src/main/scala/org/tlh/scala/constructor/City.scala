package org.tlh.scala.constructor

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/27
  * <p>
  * Github: https://github.com/tlhhup
  */
class City(val name: String) {

}

object City{

  /**
    * 构建对象
    * @param name
    * @return
    */
  def apply(name: String): City = new City(name)

  /**
    * 拆解对象
    * @param arg
    * @return
    */
  def unapply(arg: City): Option[String] = Some(arg.name)

}
