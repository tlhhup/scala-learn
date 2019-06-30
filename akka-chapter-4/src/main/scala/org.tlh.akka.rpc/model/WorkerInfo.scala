package org.tlh.akka.rpc.model

/**
  * Created by 离歌笑tlh/hu ping on 2019/6/30
  * <p>
  * Github: https://github.com/tlhhup
  */
class WorkerInfo(val id: String, val memory: Long, val cores: Int) {

  var lastHeartbeatTime: Long = _

}
