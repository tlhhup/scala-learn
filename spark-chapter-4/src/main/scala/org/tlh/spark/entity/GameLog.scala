package org.tlh.spark.entity

import java.util.Date

import org.apache.commons.lang3.time.DateUtils


/**
  * Created by 离歌笑tlh/hu ping on 2019/8/10
  * <p>
  * Github: https://github.com/tlhhup
  */
case class GameLog(
                    logType: Int,
                    date: Date,
                    ip: String,
                    nickName: String,
                    profession: String,
                    sex: String,
                    level: Int,
                    money: Int,
                    gold: String
                  ) {

}

object GameLog {

  def apply(line: String): GameLog = {
    val splits = line.split("\\|")

    val date = DateUtils.parseDate(splits(1), "yyyy'年'MM'月'dd'日',EEE,HH:mm:ss")
    val ip = if (splits.length > 8) splits(2) else ""
    val length = splits.length

    new GameLog(splits(0).toInt, date, ip, splits(length - 6), splits(length - 5), splits(length - 4), splits(length - 3).toInt, splits(length - 2).toInt, splits(length - 1))
  }

}
