package com.codecampn.nv.optin.optinmanagerlibrary.serdes;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentGivenEvent;
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

public class AdvertisementConsentGivenEventSerde implements Serde<AdvertisementConsentGivenEvent> {
    public final static AdvertisementConsentGivenEventSerde Instance = new AdvertisementConsentGivenEventSerde();

    protected final static ObjectMapper mapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    @Override
    public Serializer<AdvertisementConsentGivenEvent> serializer() {
        return new SerializerImpl();
    }

    @Override
    public Deserializer<AdvertisementConsentGivenEvent> deserializer() {
        return new DeserializerImpl();
    }

    private static class SerializerImpl implements Serializer<AdvertisementConsentGivenEvent> {

        @Override
        public byte[] serialize(String topic, AdvertisementConsentGivenEvent data) {
            try {
                return mapper.writeValueAsBytes(data);
            } catch (JsonProcessingException e) {
                throw new SerDeException("Could not serialize message on topic " + topic, e);
            }
        }
    }

    private static class DeserializerImpl implements Deserializer<AdvertisementConsentGivenEvent> {


        @Override
        public AdvertisementConsentGivenEvent deserialize(String topic, byte[] data) {
            try {
                return mapper.readValue(data, AdvertisementConsentGivenEvent.class);
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
