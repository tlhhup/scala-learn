package org.tlh.kafka

import java.time.Duration
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}

import scala.collection.JavaConverters._

/**
  * <br>
  * Created by hu ping on 7/23/2019
  * <p>
  */
object KafkaConsumer {

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "192.168.137.15:9092,192.168.137.16:9092,192.168.137.17:9092")
    props.setProperty("group.id", "test")
    props.setProperty("enable.auto.commit", "true")
    props.setProperty("auto.commit.interval.ms", "1000")
    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(Seq("test").asJava)

    while (true) {
      val records = consumer.poll(Duration.ofMillis(100))
      for (record: ConsumerRecord[String, String] <- records.asScala) {
        println(s"offset=${record.offset()}, key=${record.key()}, value=${record.value()}")
      }
    }
  }

}
