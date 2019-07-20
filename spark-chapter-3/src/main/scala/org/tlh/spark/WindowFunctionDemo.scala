package org.tlh.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by 离歌笑tlh/hu ping on 2019/7/20
  * <p>
  * Github: https://github.com/tlhhup
  */
object WindowFunctionDemo {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Window Demo").setMaster("local[2]")

    val scc = new StreamingContext(conf, Seconds(1))

    val ds = scc.socketTextStream("localhost", 9999)
    //设置每个窗口的时间(及此时只计算该窗口中的数据)，滑动时间，为设置的批次时间的整数倍
    ds.window(Seconds(3), Seconds(2))
      .print()

    scc.start()
    scc.awaitTermination()
  }

}
