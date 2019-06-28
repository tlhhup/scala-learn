package org.tlh.springboot.scala.entity

import javax.persistence._
import lombok.Data

/**
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
@Data
@Entity
@Table(name = "sys_users")
class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private var id: Int = _

  private var name: String = _

  private var age: Int = _

}
