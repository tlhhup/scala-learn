package org.tlh.scala.bound

import scala.math.{Ordered, Ordering}

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
object MyPredef {

  implicit def girl2Ordered(girl: Girl): Ordered[Girl] = new Ordered[Girl] {
    override def compare(that: Girl): Int = {
      girl.age - that.age
    }
  }

  trait GirlOrdering extends Ordering[Girl] {

    override def compare(x: Girl, y: Girl): Int = x.age - y.age

  }

  implicit object girl extends GirlOrdering

}
