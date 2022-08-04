import functions._
import org.apache.log4j.{Level, Logger}

object Boot {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("com").setLevel(Level.ERROR)
    println("Hello")
    DStream.readStream()
  }

}
