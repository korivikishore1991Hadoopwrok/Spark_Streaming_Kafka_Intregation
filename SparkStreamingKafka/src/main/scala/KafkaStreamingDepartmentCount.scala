import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.streaming.kafka._
import kafka.serializer.StringDecoder

object KafkaStreamingDepartmentCount {
  def main(args: Array[String]): Unit = {
   // application Definition
    val conf = new SparkConf().setAppName("Streaming word Count")
    val ssc = new StreamingContext(conf, Seconds(30))

    // Stream Initialisation
    val kafkaParams = Map[String, String]("metadata.broker.list"
      -> "127.0.0.1:6667, 127.0.0.2:6667") // passing the kafka brokers
    val topicsSet = Set("demoSparkKafkatopic") // passing the topic name
    val stream = KafkaUtils.createDirectStream[String, String,
      StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)
    val messages = stream.map(s => s._2)

    // Processing
    val departmentMessages = messages.
      filter(msg => {
        val endPoint = msg.split(" ")(6)
        endPoint.split("/")(1) == "department"
      })
    val departments = departmentMessages.
      map(rec => {
        val endPoint = rec.split(" ")(6)
        (endPoint.split("/")(2), 1)
      })
    val departmentTraffic = departments.
      reduceByKey((total, value) => total + value)
    departmentTraffic.saveAsTextFiles(
      "/user/cloudera/sparkstreamingHDFS1/cnt")

    ssc.start()
    ssc.awaitTermination()
  }
}
