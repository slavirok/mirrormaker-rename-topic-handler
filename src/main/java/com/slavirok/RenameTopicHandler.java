package com.slavirok;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import kafka.consumer.BaseConsumerRecord;
import kafka.tools.MirrorMaker;
import org.apache.kafka.clients.producer.ProducerRecord;

public class RenameTopicHandler implements MirrorMaker.MirrorMakerMessageHandler {
  private final HashMap<String, String> topicMap = new HashMap<String, String>();
  private long NO_TIMESTAMP = -1L;

  public RenameTopicHandler(String topics) {
    if(topics != null && !topics.isEmpty()){
      for (String topicAssignment : topics.split(";")) {
        String[] topicsArray = topicAssignment.split(",");
        if (topicsArray.length == 2) {
          topicMap.put(topicsArray[0], topicsArray[1]);
        }
      }
    }
  }

  public List<ProducerRecord<byte[], byte[]>> handle(BaseConsumerRecord record) {
      Long timestamp = record.timestamp() == NO_TIMESTAMP ? null : record.timestamp();
      return Collections.singletonList(new ProducerRecord<byte[], byte[]>(getTopicName(record.topic()), null, timestamp, record.key(), record.value(), record.headers()));
  }

  private String getTopicName(String currentTopicName){
    return topicMap.containsKey(currentTopicName) ? topicMap.get(currentTopicName) : currentTopicName;
  }
}
