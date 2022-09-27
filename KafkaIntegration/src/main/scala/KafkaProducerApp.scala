package main.scala

import java.util.Properties;
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord};
import org.apache.spark._;
import org.apache.spark.sql._;
import org.apache.spark.sql.types._;
import java.util.UUID.randomUUID;
import org.slf4j.LoggerFactory;
import ch.qos.logback.core.util.StatusPrinter;
import ch.qos.logback.classic.LoggerContext;

object KafkaProducerApp{
  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger("KafkaProducerApp");
    val conf:SparkConf = new SparkConf(false).setAppName("KafkaIntegration").setMaster("local");
    val sc = new SparkContext(conf);
    val props:Properties = new Properties();
    props.put("bootstrap.servers","localhost:9092");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("acks","all");
    val producer = new KafkaProducer[String, String](props);
    val topic = "HaveIt";
    val payTmData = sc.textFile("src/main/resources/Paytm_Statement.csv");
    logger.debug("created dataframe {}",payTmData);
    val paytmRdd = payTmData.map{
      line =>
        val col = line.split(",")
        val payTx = new PayTmTransaction(col(0), col(1), col(2), col(3),col(8))
        payTx.set_Usr_comment(col(4));
        payTx.set_Debit(col(5).toInt);
        payTx.set_Credit(col(6).toInt);
        payTx.set_Transaction_breakup(col(7));
    }
    try {
      paytmRdd.foreach(payTxRecord  => {
        logger.debug("processinbg record {}",payTxRecord);
        val id = randomUUID().toString;
        val record = new ProducerRecord[String, String](topic,id, payTxRecord.toString);
        val metadata = producer.send(record)
        printf(s"sent record(key=%s value=%s) " +
          "meta(partition=%d, offset=%d)\n",
          record.key(), record.value(),
          metadata.get().partition(),
          metadata.get().offset());
      })
    }catch{
      case e:Exception => e.printStackTrace()
    }finally {
      producer.close()
    }
  }

}

