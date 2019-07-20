package org.tlh.spark

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 累计求和
  * Created by 离歌笑tlh/hu ping on 2019/7/20
  * <p>
  * Github: https://github.com/tlhhup
  */
object WordCount {

  def updateFunc = (ite: Iterator[(String, Seq[Int], Option[Int])]) => {
    ite.map {
      case (x, y, z) => Some(y.sum + z.getOrElse(0)).map(m => (x, m)).get
    }
  }

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[2]")
    val scc = new StreamingContext(sparkConf, Seconds(5))

    //设置ck
    scc.checkpoint(".")

    val ds = scc.socketTextStream("localhost", 9999)

    ds.flatMap(_.split(" "))
      .map((_, 1))
      .updateStateByKey(updateFunc, new HashPartitioner(1), true)//调用该方法必须先设置ck
      .print()

    scc.start()
    scc.awaitTermination()
  }

}
