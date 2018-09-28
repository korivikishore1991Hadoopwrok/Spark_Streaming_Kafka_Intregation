## Prechecks  
Pass the "spark-streaming-kafka_2.10-1.6.2.jar" jar file to the kafka libary at "/usr/hdp/current/kafka/libs"(for HortonWorks) or "/usr/lib/kafka"(for Cloudera) for integration of kafka with the Spark on the cluster.  
  
Check for the metrics jar file "metrics-core-2.2.0.jar" at "/usr/hdp/current/kafka/libs"(for HortonWorks) or "/usr/lib/kafka"(for Cloudera).  
  
Check for the kafka version on the Cluster. If the version being used on the cluster is "0.10" then inorder to maintain compatibility with the "Spark 1.*". We need to download "kafka_2.10-0.8.2.1.jar" and load it on to kafka libary at "/usr/hdp/current/kafka/libs"(for HortonWorks) or "/usr/lib/kafka"(for Cloudera).  
  
## Execution  
In Scala:  
```scala
spark-submit --class KafkaStreamingDepartmentCount \
--master yarn \
--conf spark.ui.port=12890 \
--jars "/usr/hdp/2.5.0.0-1245/kafka/libs/spark-streaming-kafka_2.10-1.6.2.jar,/usr/hdp/2.5.0.0-1245/kafka/libs/kafka_2.10-0.8.2.1.jar,/usr/hdp/2.5.0.0-1245/kafka/libs/metrics-core-2.2.0.jar" \
name_of_the_program_jar.jar
```  
  
In Python:  
```python
spark-submit --master yarn \
--conf spark.ui.port=12890 \
--jars "/usr/hdp/2.5.0.0-1245/kafka/libs/spark-streaming-kafka_2.10-1.6.2.jar,/usr/hdp/2.5.0.0-1245/kafka/libs/kafka_2.10-0.8.2.1.jar,/usr/hdp/2.5.0.0-1245/kafka/libs/metrics-core-2.2.0.jar" \
StreamingKafkaDepartmentCount.py
```  
