package com.codecampn.nv.poc.streams;

import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.streams.ssl.key-password:#{null}}")
    private String sslKeyPassword;

    @Value(value = "${spring.kafka.streams.security.protocol:#{null}}")
    private String securityProtocol;

    @Value(value = "${spring.kafka.streams.ssl.trust-store-password:#{null}}")
    private String trustStorePassword;

    @Value(value = "classpath:${spring.kafka.streams.ssl.trust-store-location:#{null}}")
    private Resource trustStore;

    @Value(value = "${spring.kafka.streams.ssl.key-store-password:#{null}}")
    private String keyStorePassword;

    @Value(value = "classpath:${spring.kafka.streams.ssl.key-store-location:#{null}}")
    private Resource keyStore;

    private static final Serde<String> STRING_SERDE = Serdes.String();

    private  Map<String, Object>  getDefaultProps() throws IOException {
        Map<String, Object> props = new HashMap<>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, STRING_SERDE.getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, STRING_SERDE.getClass().getName());

        if("SSL".equals(securityProtocol)){
            props.put(SECURITY_PROTOCOL_CONFIG, securityProtocol);
            props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, sslKeyPassword);
            props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, trustStorePassword);
            props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, trustStore.getFile().getAbsolutePath());
            props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keyStorePassword);
            props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keyStore.getFile().getAbsolutePath());
        }
        return props;
    }

    @Bean("sinkApp")
    public StreamsBuilderFactoryBean sinkApp() throws IOException {
        Map<String, Object> props = getDefaultProps();
        props.put(APPLICATION_ID_CONFIG, "sink-app");
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(props));
    }

    @Bean("tableApp")
    public StreamsBuilderFactoryBean tableApp() throws IOException {
        Map<String, Object> props = getDefaultProps();
        props.put(APPLICATION_ID_CONFIG, "ktable-app");
        return new StreamsBuilderFactoryBean(new KafkaStreamsConfiguration(props));
    }
}
