package org.tlh.spark.utils

import org.tlh.spark.entity.IpInfo

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/4
  * <p>
  * Github: https://github.com/tlhhup
  */
object IpUtil {

  def ip2Long(ip: String): Long = {
    val arr = ip.split("\\.")
    var result: Long = 0
    for (index <- 0 to 3) {
      val ip = arr(index).toLong
      result += ip << ((3 - index) << 3)
    }
    result
  }


  def parseIpAddress(ip: String, arr: Array[IpInfo]): IpInfo = {
    val ipValue = ip2Long(ip)

    var low = 0
    var high = arr.length - 1
    while (low <= high) {
      val mid = (low + high) >>> 1
      val item = arr(mid)
      if (ipValue < item.startNum) {
        high = mid - 1
      } else if (ipValue > item.endNum) {
        low = mid + 1
      } else {
        return item
      }
    }
    null
  }

}
