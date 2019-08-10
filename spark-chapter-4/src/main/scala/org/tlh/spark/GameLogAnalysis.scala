package org.tlh.spark


import java.util

import org.apache.commons.lang3.time.DateUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.tlh.spark.entity.GameLog
import org.tlh.spark.utils.FilterUtil

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/10
  * <p>
  * Github: https://github.com/tlhhup
  */
object GameLogAnalysis {

  def main(args: Array[String]): Unit = {
    val input = args(0)
    val date = DateUtils.parseDate(args(1), "yyyy-MM-dd")

    val sparkConf = new SparkConf().setAppName("Game Log").setMaster("local[1]")
    val sc = new SparkContext(sparkConf)

    val parsedRdd = sc.textFile(input)
      .map(line => GameLog(line))
      .cache()

    val specifyDateLog = parsedRdd.filter(FilterUtil.specifyDate(date, _))

    //1.获取登陆用户
    val loginUsers = specifyDateLog.filter(FilterUtil.typeFilter(_, 2, 3)).cache()

    val users = loginUsers.map(_.nickName).distinct().collect()
    print(users.toList)


    //2.注册用户,首次登陆用户
    val registedUser = specifyDateLog.filter(FilterUtil.typeFilter(_, 1))
      .map(_.nickName)
      .distinct()
      .collect()
    print(registedUser)

    //3.计算用户游戏时长
    val gameTime = loginUsers.groupBy(_.nickName)
      .mapValues(ite => {
        var logs = ite.toList.sortBy(_.date)
        //如果头为下线，则添加登陆日期
        if (logs(0).logType == 3) {
          val head = logs.head.copy(date = date, logType = 2)
          logs = head :: logs
        }
        //如果尾为上线，则添加下线时间
        if (logs(logs.size - 1).logType == 2) {
          val tail = logs(logs.size - 1).copy(date = DateUtils.addDays(date, 1), logType = 3)
          logs = logs ::: List(tail)
        }
        //使用栈来存储数据
        val stack: util.Stack[GameLog] = new util.Stack[GameLog]()
        var sum: Long = 0
        logs.foreach {
          item =>
            item.logType match {
              case 2 => stack.push(item)
              case 3 => {
                try {
                  //如果为空抛异常，则不处理
                  val login = stack.pop()
                  sum += item.date.getTime - login.date.getTime
                } catch {
                  case e: Exception =>
                }
              }
            }
        }
        sum
      })
      .sortBy(_._2, false)
      .collect()

    print(gameTime.toList)

    //4.计算次日留存，昨天注册的和昨今两天登陆的用户的并集
    val yesterday = DateUtils.addDays(date, -1)
    val yesterdayRegister = parsedRdd.filter(FilterUtil.specifyDate(yesterday, _))
      .filter(FilterUtil.typeFilter(_, 1))
      .map(_.nickName)
      .distinct()

    val towDayLogin = parsedRdd.filter(FilterUtil.timeBetween(yesterday, date, _))
      .filter(FilterUtil.typeFilter(_, 2, 3))
      .map(_.nickName)
      .distinct()
    val remainUser=yesterdayRegister.intersection(towDayLogin)

    print(yesterdayRegister.count())
    print(towDayLogin.count())
    print(remainUser.count())

    sc.stop()
  }

}
