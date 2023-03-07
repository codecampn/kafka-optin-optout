package com.codecampn.nv.poc.streams.sink.serdes;

import com.codecampn.nv.poc.streams.sink.model.ConsentPreferenceKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;


public class ConsentPreferenceKeySerde implements Serde<ConsentPreferenceKey> {
    public final static ConsentPreferenceKeySerde Instance = new ConsentPreferenceKeySerde();

    protected final static ObjectMapper mapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false)
            .build();

    @Override
    public Serializer<ConsentPreferenceKey> serializer() {
        return new SerializerImpl();
    }

    @Override
    public Deserializer<ConsentPreferenceKey> deserializer() {
        return new DeserializerImpl();
    }

    private static class SerializerImpl implements Serializer<ConsentPreferenceKey> {

        @Override
        public byte[] serialize(String topic, ConsentPreferenceKey data) {
            try {
                return mapper.writeValueAsBytes(data);
            } catch (JsonProcessingException e) {
                throw new SerDeException("Could not serialize message on topic " + topic, e);
            }
        }
    }

    private static class DeserializerImpl implements Deserializer<ConsentPreferenceKey> {


        @Override
        public ConsentPreferenceKey deserialize(String topic, byte[] data) {
            try {
                return mapper.readValue(data, ConsentPreferenceKey.class);
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
