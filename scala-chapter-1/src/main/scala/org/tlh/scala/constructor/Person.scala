package org.tlh.scala.constructor

/**
  * Created by hu ping on 6/26/2019
  * <p>
  */
class Person {

  var name: String = _
  var age: Int = _

  def this(name: String, age: Int) = {//通过定义this方法实现构造函数的重载
    this()
    this.name = name
    this.age = age
  }

  override def toString = s"Person($name, $age)"
}

object Person {

  def main(args: Array[String]): Unit = {
    val person: Person = new Person()
    val person1: Person = new Person("tlh", 1)
    println(person)
    print(person1)
  }

}
