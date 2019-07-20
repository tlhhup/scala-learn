package org.tlh.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 计算每个批次下的单词数
  * Created by 离歌笑tlh/hu ping on 2019/7/20
  * <p>
  * Github: https://github.com/tlhhup
  */
object NcWordCount {

  def main(args: Array[String]): Unit = {
    //socket 需要一个receiver用于接受数据，另一个线程用于computation
    val sparkConf = new SparkConf().setAppName("Nc word count").setMaster("local[2]")

    //创建scc对象，设置时间间隔，将5秒内的数据设置为一个批次交给spark engine进行处理
    val scc = new StreamingContext(sparkConf,Seconds(5))

    //创建 Dstream对象
    val ds = scc.socketTextStream("localhost",9999)
    ds.flatMap(_.split(" "))
        .map((_,1))
        .reduceByKey(_+_)
        .print()

    //启动
    scc.start()
    //等待手动停止或者异常退出
    scc.awaitTermination()

  }

}
