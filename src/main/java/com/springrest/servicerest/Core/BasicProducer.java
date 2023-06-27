package com.springrest.servicerest.Core;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * BasicProducer
 */

 @Component
public class BasicProducer {
    private static Logger logger = LoggerFactory.getLogger(BasicProducer.class);

    @Autowired
    private KafkaProducer<String, String> getBasicStringStringProducer;
    
    public void runStringStringProducer(String topic, String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        getBasicStringStringProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception != null) {
                    logger.error(exception.getMessage(), exception);
                // } else {
                //     logger.info("Sent Record[Key="+record.key()+"::Value="+record.value()+"] MetaData:[partition:"+metadata.partition()+"::offset="+metadata.offset()+"]\n");
                }
            }
        });
    }
}
