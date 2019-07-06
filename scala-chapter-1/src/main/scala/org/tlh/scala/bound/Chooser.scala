package org.tlh.scala.bound

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
object Chooser {

  //contextbound -->找隐式参数   通过柯理化实现
  def select[T](first: T, second: T)(implicit ord: Ordering[T]): T = {
    if (ord.gt(first, second)) first else second
  }

  //viewbound -->找隐式转换
  def select1[T](first: T, second: T)(implicit ord: T => Ordered[T]): T = {
    if (first > second) first else second
  }

}
