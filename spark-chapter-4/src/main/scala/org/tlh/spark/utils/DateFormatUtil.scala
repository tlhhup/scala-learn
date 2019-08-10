package org.tlh.spark.utils

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/10
  * <p>
  * Github: https://github.com/tlhhup
  */
object DateFormatUtil {

  def milTime2Str(mil: Long): String = {
    val hour=mil/1000/60/60
    val min=mil%(1000*60*60)/1000/60
    val second=mil%(1000*60)/1000
    s"$hour Hour $min Min $second Second"
  }

}
