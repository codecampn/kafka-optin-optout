package com.codecampn.nv.poc.streams.ktable.serdes;

import com.codecampn.nv.optin.optinmanagerlibrary.domain.CustomerConsent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class CustomerConsentSerde implements Serde<CustomerConsent> {
    public final static CustomerConsentSerde Instance = new CustomerConsentSerde();

    protected final static ObjectMapper mapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    @Override
    public Serializer<CustomerConsent> serializer() {
        return new SerializerImpl();
    }

    @Override
    public Deserializer<CustomerConsent> deserializer() {
        return new DeserializerImpl();
    }

    private static class SerializerImpl implements Serializer<CustomerConsent> {

        @Override
        public byte[] serialize(String topic, CustomerConsent data) {
            try {
                return mapper.writeValueAsBytes(data);
            } catch (JsonProcessingException e) {
                throw new SerDeException("Could not serialize message on topic " + topic, e);
            }
        }
    }

    private static class DeserializerImpl implements Deserializer<CustomerConsent> {


        @Override
        public CustomerConsent deserialize(String topic, byte[] data) {
            try {
                return mapper.readValue(data, CustomerConsent.class);
            } catch (IOException e) {
                throw new SerDeException("Could not deserialize message on topic " + topic, e);
            }
        }
    }

    public static class SerDeException extends RuntimeException {
        public SerDeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
