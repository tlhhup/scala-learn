package org.tlh.spark

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.flume.FlumeUtils

/**
  * spark集群从flume中拉取数据(推荐使用)
  * Created by 离歌笑tlh/hu ping on 2019/7/20
  * <p>
  * Github: https://github.com/tlhhup
  */
object FlumePullDemo {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Flume Push Demo").setMaster("local[2]")

    val scc = new StreamingContext(sparkConf, Seconds(5))

    val ds = FlumeUtils.createPollingStream(scc, "169.172.10.102", 8888, StorageLevel.MEMORY_ONLY_SER_2)
    ds.flatMap(x => new String(x.event.getBody.array()).split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .print()

    scc.start()
    scc.awaitTermination()
  }

}
