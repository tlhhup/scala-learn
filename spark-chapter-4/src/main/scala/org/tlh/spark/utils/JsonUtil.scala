package org.tlh.spark.utils

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/10
  * <p>
  * Github: https://github.com/tlhhup
  */
object JsonUtil {

  private[this] val mapper = {
    val mapper = new ObjectMapper()
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    mapper
  }

  def json2Bean[T](json: String, _class: Class[T]): T = {
    mapper.readValue(json, _class)
  }

}
