package com.codecampn.nv.optin.optinmanagerlibrary.serdes;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentUpdateEvent;
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

public class AdvertisementConsentUpdateEventSerde implements Serde<AdvertisementConsentUpdateEvent> {
    public final static AdvertisementConsentUpdateEventSerde Instance = new AdvertisementConsentUpdateEventSerde();

    protected final static ObjectMapper mapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    @Override
    public Serializer<AdvertisementConsentUpdateEvent> serializer() {
        return new SerializerImpl();
    }

    @Override
    public Deserializer<AdvertisementConsentUpdateEvent> deserializer() {
        return new DeserializerImpl();
    }

    private static class SerializerImpl implements Serializer<AdvertisementConsentUpdateEvent> {

        @Override
        public byte[] serialize(String topic, AdvertisementConsentUpdateEvent data) {
            try {
                return mapper.writeValueAsBytes(data);
            } catch (JsonProcessingException e) {
                throw new SerDeException("Could not serialize message on topic " + topic, e);
            }
        }
    }

    private static class DeserializerImpl implements Deserializer<AdvertisementConsentUpdateEvent> {


        @Override
        public AdvertisementConsentUpdateEvent deserialize(String topic, byte[] data) {
            try {
                return mapper.readValue(data, AdvertisementConsentUpdateEvent.class);
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
