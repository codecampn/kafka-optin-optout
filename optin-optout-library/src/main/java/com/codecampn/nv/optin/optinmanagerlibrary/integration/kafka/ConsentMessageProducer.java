package com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka;

import com.codecampn.nv.optin.optinmanagerlibrary.config.Serializer;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.ConsentUpdateEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConsentMessageProducer {
    private final Logger logger = LoggerFactory.getLogger(ConsentMessageProducer.class);
    private final ObjectMapper objectMapper = Serializer.create();
    private final Producer<String, String> kafkaProducer;

    public ConsentMessageProducer(Producer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void produceConsentUpdate(ConsentUpdateEvent consentUpdate) throws ConsentUpdateFailedException {
        try {
            logger.info("Dispatching event {}", consentUpdate);
            String message = objectMapper.writeValueAsString(consentUpdate);
            ProducerRecord<String, String> record = new ProducerRecord<>("consent.preference",
                    consentUpdate.getCustomerId(),
                    message);

            logger.info("Dispatched consent event");
            // TODO: non blocking version
            var result = kafkaProducer.send(record).get(5, TimeUnit.SECONDS);

            logger.info("Dispatched consent update {}", result.offset());
        } catch (JsonProcessingException | InterruptedException | ExecutionException | TimeoutException e) {
            throw new ConsentUpdateFailedException("Could not update consent", e);
        }
    }

}