package org.tlh.scala.bound

import scala.math.Ordering

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
class ContentBound[T: Ordering] {

  def bigger(first: T, second: T): T = {
    val ord = implicitly[Ordering[T]]
    if (ord.gt(first, second)) first else second
  }

}
