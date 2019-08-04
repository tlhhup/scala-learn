package org.tlh.spark.entity

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/4
  * <p>
  * Github: https://github.com/tlhhup
  */
case class IpInfo(
                   startIp: String,
                   entIP: String,
                   startNum: Long,
                   endNum: Long,
                   isLand: String,
                   country: String,
                   province: String,
                   city: String,
                   operators: String,
                   OperatorsCode: String,
                   countryEn: String,
                   countryCode: String,
                   longitude: Double,
                   latitude: Double
                 ) extends Serializable {

}

object IpInfo {

  def apply(str: String): IpInfo = {
    val arr = str.split("\\|")
    new IpInfo(arr(0), arr(1), arr(2).toLong, arr(3).toLong, arr(4), arr(5), arr(6),
      arr(7), arr(9), arr(10), arr(11), arr(12), arr(13).toDouble, arr(14).toDouble)
  }

}
