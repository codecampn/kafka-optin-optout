package com.codecampn.nv.poc.optin;

import com.codecampn.nv.optin.optinmanagerlibrary.ConsentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class OptInProducer {

    private final Properties kafkaConsumerProperties;
    private final Properties kafkaProducerProperties;

    @Autowired
    public OptInProducer(Properties kafkaConsumerProperties, Properties kafkaProducerProperties) {
        this.kafkaConsumerProperties = kafkaConsumerProperties;
        this.kafkaProducerProperties = kafkaProducerProperties;
    }

    @Bean
    public ConsentManager consentManager() {
        return new ConsentManager("app1", kafkaProducerProperties, kafkaConsumerProperties);
    }
}
