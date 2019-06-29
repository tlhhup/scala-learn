package org.tlh.springboot.scala.service

import java.util.List

import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tlh.springboot.scala.dto.UserDto
import org.tlh.springboot.scala.entity.User
import org.tlh.springboot.scala.repository.UserRepository
import collection.JavaConverters._

/**
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
@Service
@Transactional(readOnly = true)
class UserService {

  @Autowired
  private val userRepository: UserRepository = null

  def list: List[UserDto] = {
    val users = this.userRepository.findAll.asScala
    val result = users.map(user2Dto).asJava
    result
  }

  @Transactional
  def addUser(user: UserDto): Unit = {
    val userEntity = new User()
    BeanUtils.copyProperties(user, userEntity)
    this.userRepository.save(userEntity)
  }

  @Transactional
  def deleteUser(id: Int): Unit = {
    this.userRepository.deleteById(id)
  }

  def user2Dto(user: User) = {
    val userDto = new UserDto
    BeanUtils.copyProperties(user, userDto)
    userDto
  }


}
