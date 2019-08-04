package org.tlh.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.tlh.spark.entity.IpInfo
import org.tlh.spark.utils.IpUtil

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/4
  * <p>
  * Github: https://github.com/tlhhup
  */
object IpDistribution {

  def main(args: Array[String]): Unit = {
    val ipFile = args(0)
    val input = args(1)
    val output = args(2)

    val sparkConf = new SparkConf().setAppName("Ip Distribution").setMaster("local[1]")

    val sc = new SparkContext(sparkConf)

    //加载IP信息
    val ipInfos = sc.textFile(ipFile).map(line => IpInfo(line))
    //设置广播变量
    val ips = sc.broadcast(ipInfos.collect())

    sc.textFile(input)
        .map(line=>{
          val arr = line.split("\\|")
          IpUtil.parseIpAddress(arr(1),ips.value)
        })
        .groupBy(item=>(item.country,item.province,item.city,item.longitude,item.latitude))
        .mapValues(_.size)
        .saveAsTextFile(output)

    sc.stop()
  }


}
