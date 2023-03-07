package com.codecampn.nv.poc.streams.sink.serdes;

import com.codecampn.nv.poc.streams.sink.model.ConsentPreferenceValue;
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

public class ConsentPreferenceValueSerde implements Serde<ConsentPreferenceValue> {
    public final static ConsentPreferenceValueSerde Instance = new ConsentPreferenceValueSerde();

    protected final static ObjectMapper mapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false)
            .build();

    @Override
    public Serializer<ConsentPreferenceValue> serializer() {
        return new SerializerImpl();
    }

    @Override
    public Deserializer<ConsentPreferenceValue> deserializer() {
        return new DeserializerImpl();
    }

    private static class SerializerImpl implements Serializer<ConsentPreferenceValue> {

        @Override
        public byte[] serialize(String topic, ConsentPreferenceValue data) {
            if (data == null) {
                return null;
            }
            try {
                return mapper.writeValueAsBytes(data);
            } catch (JsonProcessingException e) {
                throw new SerDeException("Could not serialize message on topic " + topic, e);
            }
        }
    }

    private static class DeserializerImpl implements Deserializer<ConsentPreferenceValue> {


        @Override
        public ConsentPreferenceValue deserialize(String topic, byte[] data) {
            try {
                if (data == null) {
                    return null;
                }
                return mapper.readValue(data, ConsentPreferenceValue.class);
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
