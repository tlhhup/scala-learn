package org.tlh.scala.bound

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
object Chooser {

  //contextbound -->找隐式参数   通过柯理化实现
  def select[T](first: T, second: T)(implicit ord: Ordering[T]): T = {
    //主要是调用ordering的方法来实现大小的比较，所以只需要有这个对象(变量)即可
    if (ord.gt(first, second)) first else second
  }

  //viewbound -->找隐式转换
  def select1[T](first: T, second: T)(implicit ord: T => Ordered[T]): T = {
    //主要是直接通过Ordered的方法来实现大小的比较，所以需要一个方法来将数据转换为Ordered类型
    if (first > second) first else second
  }

}
