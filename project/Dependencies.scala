import sbt._

object Dependencies {
  val sparkVersion = "3.1.0"

  val core = "org.apache.spark" %% "spark-core" % sparkVersion
  val sql = "org.apache.spark" %% "spark-sql" % sparkVersion
  val avro = "org.apache.spark" %% "spark-avro" % sparkVersion
  val stream = "org.apache.spark" %% "spark-streaming" % sparkVersion
  val streamKafka = "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion

  val all: Seq[ModuleID] = Seq(core, sql, avro, stream, streamKafka)
}
