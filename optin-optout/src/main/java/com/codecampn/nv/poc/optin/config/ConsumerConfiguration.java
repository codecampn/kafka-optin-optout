package com.codecampn.nv.poc.optin.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class ConsumerConfiguration {
    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value(value = "${spring.kafka.consumer.enable-auto-commit}")
    private String enableAutoCommit;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value(value = "${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value(value = "${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Value(value = "${spring.kafka.producer.ssl.key-password:#{null}}")
    private String sslKeyPassword;

    @Value(value = "${spring.kafka.producer.security.protocol:#{null}}")
    private String securityProtocol;

    @Value(value = "${spring.kafka.producer.ssl.trust-store-password:#{null}}")
    private String trustStorePassword;

    @Value(value = "classpath:${spring.kafka.producer.ssl.trust-store-location:#{null}}")
    private Resource trustStore;

    @Value(value = "${spring.kafka.producer.ssl.key-store-password:#{null}}")
    private String keyStorePassword;

    @Value(value = "classpath:${spring.kafka.producer.ssl.key-store-location:#{null}}")
    private Resource keyStore;

    @Bean
    public Properties kafkaConsumerProperties() throws IOException {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        if("SSL".equals(securityProtocol)){
            properties.put("security.protocol", securityProtocol);
            properties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, sslKeyPassword);
            properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, trustStorePassword);
            properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, trustStore.getFile().getAbsolutePath());
            properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keyStorePassword);
            properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keyStore.getFile().getAbsolutePath());
        }
        return properties;
    }

}
