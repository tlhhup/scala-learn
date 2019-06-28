package org.tlh.springboot.scala.entity

import javax.persistence._

import scala.beans.BeanProperty

/**
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
@Entity
@Table(name = "sys_users")
class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Int = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var age: Int = _

}
