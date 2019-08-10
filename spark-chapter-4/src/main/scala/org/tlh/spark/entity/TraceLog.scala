package org.tlh.spark.entity

import com.fasterxml.jackson.annotation.JsonProperty

import scala.beans.BeanProperty

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/10
  * <p>
  * Github: https://github.com/tlhhup
  */
case class TraceLog() {
  @BeanProperty var time: String = null
  @BeanProperty var client: String = null
  @BeanProperty var domain: String = null
  @BeanProperty var url: String = null
  @BeanProperty var title: String = null
  @BeanProperty var referrer: String = null
  @BeanProperty var sh: Long = 0
  @BeanProperty var sw: Long = 0
  @BeanProperty var cd: Long = 0
  @BeanProperty var lang: String = null
  @BeanProperty var ua: String = null
  @BeanProperty var trace: String = null
  @BeanProperty
  @JsonProperty("type") var logType: Int = 0

}
