package org.tlh.spark.utils

import java.util.Date

import org.apache.commons.lang3.time.DateUtils
import org.tlh.spark.entity.GameLog

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/10
  * <p>
  * Github: https://github.com/tlhhup
  */
object FilterUtil {

  def specifyDate(date: Date, log: GameLog): Boolean = {
    DateUtils.isSameDay(date, log.date)
  }

  def timeBetween(start: Date, end: Date, log: GameLog): Boolean = {
    log.date.after(start) && log.date.before(end)
  }

  def typeFilter(log: GameLog, needType: Int*): Boolean = {
    needType.contains(log.logType)
  }

}
