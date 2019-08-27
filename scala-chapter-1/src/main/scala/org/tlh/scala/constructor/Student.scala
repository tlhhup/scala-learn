package org.tlh.scala.constructor

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/27
  * <p>
  * Github: https://github.com/tlhhup
  */
class Student(name: String, age: Int, address: String) {//主构造器

  /**
    * Parameters without val or var are private values, visible only within the class.
    * 1. 没有使用val或var修饰，默认为val，表示不可变，不会生成get/set，同时只能在当前类中可见
    * 2. 使用val或var修饰会生成get/set方法
    */

  private[this] var id: Int = _ //只能在当前对象中可见,伴生对象中也不可见

  var num:String=_

  //定义辅助构造器
  def this(name: String, age: Int, address: String,id:Int) = {
    //首先必须调用主构造器
    this(name, age, address)
    this.id=id
  }

}

object Student{

  /**
    * 用于直接创建对象
    * @param name
    * @param age
    * @param address
    * @return
    */
  def apply(name: String, age: Int, address: String): Student = new Student(name, age, address)

  def apply(name: String, age: Int, address: String,id:Int): Student = new Student(name, age, address,id)

  def main(args: Array[String]): Unit = {
    val student=Student("tlh",10,"成都")
    print(student.num)
  }

}
