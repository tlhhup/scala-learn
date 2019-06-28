package org.tlh.springboot.scala.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.tlh.springboot.scala.entity.User

/**
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
trait UserRepository extends JpaRepository[User, Int] {

}
