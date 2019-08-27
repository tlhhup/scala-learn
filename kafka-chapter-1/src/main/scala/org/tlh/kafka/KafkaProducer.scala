package org.tlh.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * <br>
  * Created by hu ping on 7/23/2019
  * <p>
  */
object KafkaProducer {

  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "192.168.137.15:9092,192.168.137.16:9092,192.168.137.17:9092")
    props.put("acks", "all")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    for (i <- 1 until 100) {
      producer.send(new ProducerRecord[String, String]("test", i.toString, i.toString))
    }

    producer.close()
  }

}
