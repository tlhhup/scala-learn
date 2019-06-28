package org.tlh.springboot.scala.dto

import scala.beans.BeanProperty


/**
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
class UserDto extends Serializable {

  @BeanProperty
  var name: String = _

  @BeanProperty
  var age: Int = _


}
