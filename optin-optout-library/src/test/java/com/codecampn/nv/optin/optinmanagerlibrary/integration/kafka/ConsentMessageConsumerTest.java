package com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka;

import com.codecampn.nv.optin.optinmanagerlibrary.config.Serializer;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentRevokedEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.ConsentUpdateEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ConsentMessageConsumerTest {
    public static final String TOPIC = "consent.preference";

    final ObjectMapper mapper = Serializer.create();

    final TopicPartition topicPartition = new TopicPartition(TOPIC, 0);

    ConsentMessageConsumer consumer;
    MockConsumer<String, String> mockConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);

    AtomicInteger offset = new AtomicInteger(0);

    final AdvertisementConsentRevokedEvent consentObject = AdvertisementConsentRevokedEvent.builder()
            .customerId("customer")
            .channel("channel")
            .source("source")
            .build();

    @BeforeEach()
    public void setup() {
        consumer = new ConsentMessageConsumer(mockConsumer);
    }

    @Test
    @DisplayName("subscribe should pull from a kafka topic in the background and complete after stop")
    public void subscribe_shouldPollFromKafkaTopicAndCloseProperly() {
        TestSubscriber<Event> testSubscriber = new TestSubscriber<>();

        mockConsumer.schedulePollTask(consumer::stop);

        consumer.consume(TOPIC).subscribe(testSubscriber);
        testSubscriber.awaitDone(5, TimeUnit.SECONDS);

        testSubscriber.assertComplete();
        assertTrue(mockConsumer.closed(), "Kafka consumer has not been closed after stop");
    }

    @Test
    @DisplayName("subscribe should emit events when unread messages are available in the partition")
    public void subscribe_shouldEmitEventsFromTopic() {
        TestSubscriber<Event> testSubscriber = new TestSubscriber<>();

        mockConsumer.schedulePollTask(this::addTopicPartitionsAssignmentAndAddConsumerRecords);

        mockConsumer.schedulePollTask(() -> createRecord(consentObject, 0));
        mockConsumer.schedulePollTask(consumer::stop);

        consumer.consume(TOPIC).subscribe(testSubscriber);
        testSubscriber.awaitDone(5, TimeUnit.SECONDS);

        testSubscriber.assertValueAt(0, value -> value.equals(consentObject));
    }

    @Test
    @DisplayName("should poll in a separate thread")
    public void subscribe_shouldPollInSeparateThread() {
        TestSubscriber<Event> testSubscriber = new TestSubscriber<>();

        mockConsumer.schedulePollTask(this::addTopicPartitionsAssignmentAndAddConsumerRecords);

        mockConsumer.schedulePollTask(() -> createRecord(consentObject, 500));
        mockConsumer.schedulePollTask(consumer::stop);

        consumer.consume(TOPIC).subscribe(testSubscriber);
        testSubscriber.awaitDone(5, TimeUnit.SECONDS);

        testSubscriber.assertValueAt(0, value -> value.equals(consentObject));
    }

    private void addTopicPartitionsAssignmentAndAddConsumerRecords() {
        final Map<TopicPartition, Long> beginningOffsets = new HashMap<>();
        beginningOffsets.put(topicPartition, 0L);
        mockConsumer.rebalance(Collections.singletonList(topicPartition));
        mockConsumer.updateBeginningOffsets(beginningOffsets);
    }

    private void createRecord(ConsentUpdateEvent event, int timeoutMillis) {
        try {
            Thread.sleep(timeoutMillis);
            String value = mapper.writeValueAsString(event);
            mockConsumer.addRecord(new ConsumerRecord<>(TOPIC, 0, offset.getAndAdd(1), event.getCustomerId(), value));
        } catch (JsonProcessingException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}