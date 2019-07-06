package org.tlh.scala.bound

import math.Ordered

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
class ViewBound[T <% Ordered[T]] {

  def bigger(first: T, second: T): T = {
    if (first > second) first else second
  }

}
