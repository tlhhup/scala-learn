package org.tlh.spark

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 直链到kafka的broker获取数据，此时应该保证topic的分区数和worker的数量相等，提供效率，是的Kafka的一个分区对应rdd的一个分区
  * Created by 离歌笑tlh/hu ping on 2019/7/20
  * <p>
  * Github: https://github.com/tlhhup
  */
object DirectKafkaDemo {

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("Kafka").setMaster("local[3]")
    val scc = new StreamingContext(sparkConf, Seconds(5))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "169.172.10.106:9092,169.172.10.107:9092,169.172.10.108:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "test",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("topicA")
    val stream = KafkaUtils.createDirectStream[String, String](
      scc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    stream.map(record => (record.key, record.value))
      .print()

    scc.start()
    scc.awaitTermination()
  }

}
