package functions

import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.KafkaUtils.createRDD
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.{SparkConf, SparkContext}
//import java.util.List
//import scala.collection.JavaConverters._
import system._

object DStream extends Serializable {

  def readStream() : Unit = {
    val columns = Properties.schemaExpedia.fieldNames.toSeq

    def threatLine(spark: SparkSession, str: String)  = {
      import spark.implicits._
      val columns = Properties.schemaExpedia.fieldNames
      println("treating line... " + str)
      /*val dfSeq = str.split(",")
        .map(_.replace("[", ""))
        .map(_.replace("]", ""))
        .toSeq
      val rdd = spark.sparkContext.parallelize(dfSeq)
      val df = rdd.toDF()*/
      val cols = Seq("language","users_count")
      val data = Seq(("Java", "20000"), ("Python", "100000"), ("Scala", "3000"))
      val rdd = spark.sparkContext.parallelize(data)
      val dfFromRDD1 = rdd.toDF()
      dfFromRDD1.printSchema()
    }

    def threatRDD(spark: SparkSession, rdd: RDD[String]): DataFrame = {
      import spark.implicits._
      val rddFlat = rdd.flatMap(l => l.split(","))
      //val rddT = spark.sparkContext.parallelize(rddFlat.collect.toSeq.transpose)
      val df = rddFlat.toDF()
      df.show()
      df
    }

    val spark = SparkSession.builder()
      .appName("DFReader")
      .master("local[*]")
      .getOrCreate()
    import spark.implicits._
    val ssc = new StreamingContext(spark.sparkContext, Seconds(10))

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

    val lines = stream.map(_.value)
    lines.foreachRDD(_.foreach(println))
    //lines.foreachRDD(rdd => threatRDD(spark, rdd))
    lines.foreachRDD(_.foreach(v => threatLine(spark, v)))

    ssc.start()
    ssc.awaitTermination()
  }
}
