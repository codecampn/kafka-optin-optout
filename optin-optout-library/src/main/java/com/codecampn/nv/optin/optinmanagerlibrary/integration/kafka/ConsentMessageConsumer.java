package com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka;

import com.codecampn.nv.optin.optinmanagerlibrary.config.Serializer;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentGivenEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentRevokedEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static java.util.Collections.singletonList;

public class ConsentMessageConsumer {
    private final static Logger logger = LoggerFactory.getLogger(ConsentMessageConsumer.class);

    private final Consumer<String, String> consumer;
    private ReplaySubject<Boolean> stop = ReplaySubject.<Boolean>create(1);

    private ObjectMapper mapper = Serializer.create();

    public ConsentMessageConsumer(Consumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public Flowable<Event> consume(String topic) {
        return consume(topic, 0);
    }

    public Flowable<Event> consume(String topic, long offset) {
        logger.debug("Consuming events from topic {}", topic);

        return Flowable.<Event>fromPublisher(subscriber -> {
            consumer.subscribe(singletonList(topic));
            while (!Boolean.TRUE.equals(stop.getValue())) {
                logger.debug("Polling for new events");
                ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(1000));
                if (poll.isEmpty()) {
                    continue;
                }
                poll.forEach(record -> publishRecord(record, subscriber));
                consumer.commitSync();
            }
            logger.debug("Polling finished");
        }).takeUntil(stop.toFlowable(BackpressureStrategy.LATEST)).doOnComplete(() -> {
            logger.info("Consumer closed");
            consumer.close();
        }).subscribeOn(Schedulers.single());
    }

    private void publishRecord(ConsumerRecord<String, String> record, Subscriber<? super Event> subscriber) {
        try {
            var event = mapper.readTree(record.value());
            var type = event.get("type").asText();
            if (logger.isDebugEnabled()) {
                logger.debug("Got event {}", type);
            }
            if (AdvertisementConsentGivenEvent.EVENT_TYPE.equals(type)) {
                subscriber.onNext(mapper.readValue(record.value(), AdvertisementConsentGivenEvent.class));
            } else if (AdvertisementConsentRevokedEvent.EVENT_TYPE.equals(type)) {
                subscriber.onNext(mapper.readValue(record.value(), AdvertisementConsentRevokedEvent.class));
            } else {
                logger.info("Skipping unknown event {}", event);
            }
        } catch (JsonProcessingException e) {
            logger.error("Error while consuming kafka topic", e);
            subscriber.onError(e);
            throw new EventProcessingFailedException("Could not read record", e);
        }
    }

    public void stop() {
        logger.info("Consumer stop requested");
        //noinspection ConstantConditions
        stop.onNext(true);
    }
}
