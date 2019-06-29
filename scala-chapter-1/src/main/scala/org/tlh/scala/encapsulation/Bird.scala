package org.tlh.scala.encapsulation

/**
  * Created by 离歌笑tlh/hu ping on 2019/6/29
  * <p>
  * Github: https://github.com/tlhhup
  */
class Bird(name: String, age: Int) extends Animal(name: String, age: Int) {

  override def run: Unit = {
    println(s"this is a bird $name running")
  }
}
