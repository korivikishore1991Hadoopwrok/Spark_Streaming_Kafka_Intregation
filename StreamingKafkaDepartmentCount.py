from pyspark import SparkConf, SparkContext
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils

import sys

conf = SparkConf(). \
setAppName("Streaming Department Count"). \
setMaster("yarn-client")
sc = SparkContext(conf=conf)
ssc = StreamingContext(sc, 30)

topics = ["fkdemodg"]
brokerList = {"metadata.broker.list" : "nn01.itversity.com:6667,nn02.itversity.com:6667,rm01.itversity.com:6667"}
directKafkaStream = KafkaUtils.createDirectStream(ssc, topics, brokerList)

messages = directKafkaStream.map(lambda msg: msg[1])
departmentMessages = messages. \
filter(lambda msg: msg.split(" ")[6].split("/")[1] == "department")
departmentNames = departmentMessages. \
map(lambda msg: (msg.split(" ")[6].split("/")[2], 1))
from operator import add
departmentCount = departmentNames. \
reduceByKey(add)

outputPrefix = "/user/cloudera/sparkstreamingHDFS1/cnt"
departmentCount.saveAsTextFiles(outputPrefix)

ssc.start()
ssc.awaitTermination()