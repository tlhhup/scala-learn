package org.tlh.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.tlh.spark.entity.TraceLog
import org.tlh.spark.utils.JsonUtil

/**
  * Created by 离歌笑tlh/hu ping on 2019/8/10
  * <p>
  * Github: https://github.com/tlhhup
  */
object ReferrerDistribution {

  def main(args: Array[String]): Unit = {
    val input = args(0)

    val sparkConf = new SparkConf().setAppName("Trace log").setMaster("local[1]")
    val sc = new SparkContext(sparkConf)

    val results=sc.textFile(input)
      .map(line => {
        val trace: TraceLog = JsonUtil.json2Bean(line, classOf[TraceLog])
        (trace.referrer, 1)
      })
      .reduceByKey(_ + _)
      .collect()

    print(results.toList)

    sc.stop()
  }

}
