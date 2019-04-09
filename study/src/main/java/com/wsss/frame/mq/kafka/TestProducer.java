package com.wsss.frame.mq.kafka;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.wsss.basic.util.date.DateUtils;

public class TestProducer {
	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.10.129.149:9092");
		// The "all" setting we have specified will result in blocking on the
		// full commit of the record, the slowest but most durable setting.
		// “所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。
		props.put("acks", "all");
		// 如果请求失败，生产者也会自动重试，即使设置成０ the producer can automatically retry.
		props.put("retries", 0);

		// The producer maintains buffers of unsent records for each partition.
		props.put("batch.size", 16384);
		// 默认立即发送，这里这是延时毫秒数
		props.put("linger.ms", 1);
		// 生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
		props.put("buffer.memory", 33554432);
		// The key.serializer and value.serializer instruct how to turn the key
		// and value objects the user provides with their ProducerRecord into
		// bytes.
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		// 创建kafka的生产者类
		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		// 生产者的主要方法
		// close();//Close this producer.
		// close(long timeout, TimeUnit timeUnit); //This method waits up to
		// timeout for the producer to complete the sending of all incomplete
		// requests.
		// flush() ;所有缓存记录被立刻发送
		for (int i = 0; i < 10000000; i++) {
			//这里平均写入４个分区
			
			long time = new Date().getTime() - 8*3600*1000;
			String date = DateUtils.formatDatetime(new Date(time), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			String msg = "{\"flag\":\"0xc5574f33\", \"offset\":\"37890247\", \"operation_type\":\""+(i%3 == 0 ? "FORCEUPLOADFILE":"WWWWWW")+"\", \"consuming_time\":\"0\", "
					+ "\"level\":\"INFO\", \"input_type\":\"log\", \"Identification\":\"F\", \"source\":\"/opt/jar_lfs-acceptor/logs/lfs-acceptor.log\", "
					+ "\"type\":\"businesstp-settle-core\", \"token\":\"5CZL706L0YpgqP7u47k1Ew==\", \"Result\":\""+i+"\", "
					+ "\"@timestamp\":\""+date+"\", \"appname\":\"lfsserver\", \"@version\":\"1\", \"host\":\"lfs-acceptor-6-69\", "
					+ "\"occurrenceTime2\":\""+System.currentTimeMillis()+"\"}";
			producer.send(new ProducerRecord<String, String>("demo", msg));
			producer.flush();
			System.out.println("send");
			Thread.sleep(100);
		}
		producer.close();
	}
}