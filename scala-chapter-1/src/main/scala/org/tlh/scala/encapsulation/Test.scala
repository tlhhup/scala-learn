package org.tlh.scala.encapsulation

import java.util.ArrayList
import collection.JavaConverters._

/**
  * Created by 离歌笑tlh/hu ping on 2019/6/29
  * <p>
  * Github: https://github.com/tlhhup
  */
object Test {

  def main(args: Array[String]): Unit = {
    /*val animal: Animal = new Dog("tom", 10)

    run(animal)

    val bird = new Bird("bird", 10)
    run(bird)*/
    javaList
  }

  def run[A <: Animal](a: A) = {
    a.run
  }

  def javaList = {
    val bird = new ArrayList[Bird]
    bird.add(new Bird("bird1", 1))
    bird.add(new Bird("bird2", 2))
    bird.add(new Bird("bird3", 3))

    val results = bird.asScala.map(item => {
      val cat = new Cat(item.name, item.age)
      cat
    })
    results
  }

}
