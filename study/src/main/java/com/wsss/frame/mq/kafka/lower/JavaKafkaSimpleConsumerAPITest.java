package com.wsss.frame.mq.kafka.lower;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerry on 12/21.
 */
public class JavaKafkaSimpleConsumerAPITest {
    public static void main(String[] args) {
        JavaKafkaSimpleConsumerAPI example = new JavaKafkaSimpleConsumerAPI();
        long maxReads = 300;
        String topic = "demo";
        int partitionID = 0;

        MyKafkaTopicPartitionInfo topicPartitionInfo = new MyKafkaTopicPartitionInfo(topic, partitionID);
        List<MyKafkaBrokerInfo> seeds = new ArrayList<MyKafkaBrokerInfo>();
        //seeds.add(new MyKafkaBrokerInfo("10.10.129.149", 9092));
        seeds.add(new MyKafkaBrokerInfo("127.0.0.1", 9092));
        try {
            example.run(maxReads, topicPartitionInfo, seeds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 获取该topic所属的所有分区ID列表
        System.out.println(example.fetchTopicPartitionIDs(seeds, topic, 100000, 64 * 1024, "client-id"));
    }
}