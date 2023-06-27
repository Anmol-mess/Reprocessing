package com.springrest.servicerest.Core;


import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

@Configuration
public class KafkaProducerConfigurations {
    
    @Bean
    public KafkaProducer<String, String> getBasicStringStringProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "b-2.devmskcluster.0ij1lx.c4.kafka.us-east-2.amazonaws.com:9092,b-1.devmskcluster.0ij1lx.c4.kafka.us-east-2.amazonaws.com:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducerExample");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 10000);
        return new KafkaProducer<String, String>(properties);
    }
}
