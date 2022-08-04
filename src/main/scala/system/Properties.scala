package system

import java.util.Properties

object Properties {

  val propertiesPG = new Properties()
  propertiesPG.setProperty("user", "user")
  propertiesPG.setProperty("password", "user")
  propertiesPG.setProperty("driver", "org.postgresql.Driver")

  val urlPG = "jdbc:postgresql://localhost:5432/demo"
  val pathExpedia = "C:\\Users\\mvchernov\\work\\201source\\07\\expedia\\"
  val pathHotelWeather = "C:\\Users\\mvchernov\\work\\201source\\07\\hotel-weather\\"
  val tablePGExpedia = "expedia"
  val tablePGHotelWeather = "hotel_weather"

  val propertiesKafka = new Properties()
  propertiesKafka.put("bootstrap.servers", "localhost:9092")
  propertiesKafka.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  propertiesKafka.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val kafkaTopic = "testtopic"

}
