package com.codecampn.nv.optin.optinmanagerlibrary.integration;

import com.codecampn.nv.optin.optinmanagerlibrary.config.Serializer;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentGivenEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentRevokedEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentMessageProducer;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentUpdateFailedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsentMessageProducerTest {

    MockProducer mockProducer = new MockProducer(true, new StringSerializer(), new StringSerializer());

    ConsentMessageProducer optinKafkaProducer = new ConsentMessageProducer(mockProducer);
    ObjectMapper objectMapper = Serializer.create();

    @Test
    @DisplayName("produceContentUpdate should produce a JSON serialized OptOut Message")
    public void optOut_shouldProduceAnOptOutMessage() throws JsonProcessingException, ConsentUpdateFailedException {
        AdvertisementConsentRevokedEvent optOutMessage = AdvertisementConsentRevokedEvent.builder()
                .customerId("customer1")
                .source("source")
                .channel("channel")
                .build();
        optinKafkaProducer.produceConsentUpdate(optOutMessage);

        AdvertisementConsentRevokedEvent retrievedOptOut = objectMapper.readValue(getProducedValue(0), AdvertisementConsentRevokedEvent.class);
        assertEquals(retrievedOptOut, optOutMessage);
    }

    @Test
    @DisplayName("produceContentUpdate should produce a JSON serialized OptIn Message")
    public void optIn_shouldProduceAnOptInMessage() throws JsonProcessingException, ConsentUpdateFailedException {
        AdvertisementConsentGivenEvent optInMessage = AdvertisementConsentGivenEvent.builder()
                .customerId("customer1")
                .source("source")
                .channel("channel")
                .build();

        optinKafkaProducer.produceConsentUpdate(optInMessage);

        AdvertisementConsentGivenEvent retrievedOptOut = objectMapper.readValue(getProducedValue(0), AdvertisementConsentGivenEvent.class);
        assertEquals(retrievedOptOut, optInMessage);
    }



    @SuppressWarnings("unchecked")
    private String getProducedValue(int nr) {
        assertTrue(mockProducer.history().size() > nr, "Less than %s entries in producer".formatted(nr + 1));
        Object producedObject = mockProducer.history().get(nr);
        if (!(producedObject instanceof ProducerRecord)) {
            fail("Wrong type in mockProducer");
        }
        return ((ProducerRecord<String, String>) producedObject).value();
    }
}