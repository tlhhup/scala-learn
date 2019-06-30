package org.tlh.akka.rpc

/**
  * Created by 离歌笑tlh/hu ping on 2019/6/30
  * <p>
  * Github: https://github.com/tlhhup
  */
package object model {

  trait RemoteMessage extends Serializable

  //注册Worker   worker-->master
  case class RegisterWorker(id: String, memory: Long, cores: Int) extends RemoteMessage

  //注册响应 master-->worker
  case object WorkRegistered extends RemoteMessage

  //Worker -> self
  case object SendHeartbeat

  //worker的心跳检查
  case class HeartBeat(id: String) extends RemoteMessage

  // Master -> self
  case object CheckTimeOutWorker

}
