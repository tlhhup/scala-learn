package org.tlh.springboot.scala.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._
import org.tlh.springboot.scala.dto.UserDto
import org.tlh.springboot.scala.entity.User
import org.tlh.springboot.scala.service.UserService
import java.util.List

/**
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
@RestController
class UserController {

  @Autowired
  private val userService: UserService = null

  @GetMapping(value = Array("/index"))
  def index: String = {
    "hello,world!"
  }

  @PostMapping(value = Array("/addUser"))
  def addUser(@RequestBody user: UserDto): Unit = {
    this.userService.addUser(user)
  }

  @DeleteMapping(value = Array("/delete/{id}"))
  def deleteUserById(@PathVariable(name = "id") id: Int) = {
    this.userService.deleteUser(id)
  }

  @GetMapping(value = Array("/list"))
  def list: List[UserDto] = {
    this.userService.list
  }

}
