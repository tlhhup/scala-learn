package org.tlh.akka

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/6
  * <p>
  * Github: https://github.com/tlhhup
  */
package object model {

  trait RemoteMessage extends Serializable

  case class RegisterMessage(id: String) extends RemoteMessage

  case class Article(id: String, content: String) extends RemoteMessage

  case class CountResult(id: String, count: Int) extends RemoteMessage

}
