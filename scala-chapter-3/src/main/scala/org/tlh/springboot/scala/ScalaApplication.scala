package org.tlh.springboot.scala

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
  * <br>
  * Created by hu ping on 6/28/2019
  * <p>
  */
@SpringBootApplication
@EnableTransactionManagement
class ScalaApplication {

}

object ScalaApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[ScalaApplication])
  }
}