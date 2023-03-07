package com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;

public class KafkaClientFactory {

    public static Producer<String, String> createKafkaProducer(Properties properties) {
        return new KafkaProducer<>(properties);
    }

    public static Consumer<String, String> createKafkaConsumer(Properties properties) {
        return new KafkaConsumer<>(properties);
    }
}
