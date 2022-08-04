package functions

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.KafkaUtils.createRDD
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.{SparkConf, SparkContext}
import system._

object DStream {
  def readStream() : Unit = {
    /*val spark = SparkSession.builder()
      .master("local[*]")
      .appName("SparkDStream")
      .getOrCreate()*/

    val conf = new SparkConf().setAppName("SparkDStream").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(10))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "consumer-group-2"
    )

    val topic = Set(Properties.kafkaTopic)
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topic, kafkaParams))

    stream.map(record => (println(record.key.length), println(record.value.length)))
    val lines = stream.map(_.value)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)
    wordCounts.print()
    println(wordCounts.reduceByKey(_ + _))

    ssc.start()             // Start the computation
    ssc.awaitTermination()  // Wait for the computation to terminate
    /*val offsetRanges = Array(
      // topic, partition, inclusive starting offset, exclusive ending offset
      OffsetRange("testtopic", 0, 0, 100),
      OffsetRange("testtopic", 1, 0, 100)
    )
    val rdd = KafkaUtils.createRDD[String, String](sc, kafkaParams, offsetRanges, PreferConsistent)*/


  }

}
