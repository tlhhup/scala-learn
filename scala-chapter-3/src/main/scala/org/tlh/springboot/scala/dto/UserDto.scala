package org.tlh.springboot.scala.dto

import lombok.Data

/**
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
@Data
class UserDto extends Serializable {

  private var name: String = _

  private var age: Int = _


}
