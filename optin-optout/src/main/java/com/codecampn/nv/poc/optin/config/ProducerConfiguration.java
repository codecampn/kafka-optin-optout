package com.codecampn.nv.poc.optin.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class ProducerConfiguration {
    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value(value = "${spring.kafka.producer.client-id}")
    private String clientId;

    @Value(value = "${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value(value = "${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

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
    public Properties kafkaProducerProperties() throws IOException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
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
