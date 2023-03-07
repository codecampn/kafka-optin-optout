package com.codecampn.nv.poc.streams.ktable.processor;

import com.codecampn.nv.optin.optinmanagerlibrary.domain.ChannelConsent;
import com.codecampn.nv.optin.optinmanagerlibrary.domain.CustomerConsent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentGivenEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentRevokedEvent;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.state.KeyValueStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Properties;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ConsentAggregateProcessorTest {

    private TopologyTestDriver topologyTestDriver;
    private TestInputTopic<String, String> inputTopic;
    private KeyValueStore<String, CustomerConsent> store;
    private static final Serde<String> STRING_SERDE = Serdes.String();
    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    @BeforeEach
    void setUp() {
        ConsentAggregateProcessor consentAggregateProcessor = new ConsentAggregateProcessor();
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        consentAggregateProcessor.buildPipeline(streamsBuilder);
        Topology topology = streamsBuilder.build();
        this.topologyTestDriver = new TopologyTestDriver(topology, new Properties());
        this.inputTopic = topologyTestDriver.createInputTopic(
                "consent.preference",
                STRING_SERDE.serializer(),
                STRING_SERDE.serializer());

        this.store = topologyTestDriver.getKeyValueStore("consent.preference.store");
    }

    @Test
    void itShouldAddChannelConsentToStateStore_whenAdvertisementConsentGivenEventIsThrown() throws JsonProcessingException {

        AdvertisementConsentGivenEvent advertisementConsentGivenEvent = AdvertisementConsentGivenEvent.builder()
                .customerId("userId")
                .target("testmail@test.de")
                .channel("mail")
                .time(Instant.ofEpochSecond(1677613970, 453204838))
                .source("app1")
                .build();

        String event = mapper.writeValueAsString(advertisementConsentGivenEvent);

        inputTopic.pipeInput("userId", event);

        CustomerConsent expected = CustomerConsent.builder()
                .customerId("userId")
                .channelConsents(Set.of(ChannelConsent.builder()
                        .channel("mail")
                        .consentDate(Instant.ofEpochSecond(1677613970, 453204838))
                        .target("testmail@test.de")
                        .source("app1")
                        .build()))
                .build();

        assertThat(store.get("userId")).isEqualTo(expected);
    }

    @Test
    void itShouldRemoveChannelConsentFromStateStore_whenAdvertisementConsentRevokedEventIsThrown() throws JsonProcessingException {

        CustomerConsent givenConsent = CustomerConsent.builder()
                .customerId("userId")
                .channelConsents(Set.of(ChannelConsent.builder()
                        .channel("mail")
                        .consentDate(Instant.ofEpochSecond(1677613970, 453204838))
                        .target("testmail@test.de")
                        .source("app1")
                        .build()))
                .build();
        store.put("userId", givenConsent);

        AdvertisementConsentRevokedEvent advertisementConsentRevokedEvent = AdvertisementConsentRevokedEvent.builder()
                .time(Instant.ofEpochSecond(1677613970, 453204838))
                .customerId("userId")
                .target("testmail@test.de")
                .source("app1")
                .build();

        String event = mapper.writeValueAsString(advertisementConsentRevokedEvent);

        inputTopic.pipeInput("userId", event);

        CustomerConsent expected = CustomerConsent.builder()
                .customerId("userId")
                .channelConsents(Set.of())
                .build();

        assertThat(store.get("userId")).isEqualTo(expected);
    }

    @Test
    void itShouldUpdateChannelConsent_whenConsentForSameTargetExists() throws JsonProcessingException {

        CustomerConsent givenConsent = CustomerConsent.builder()
                .customerId("userId")
                .channelConsents(Set.of(ChannelConsent.builder()
                        .channel("mail")
                        .consentDate(Instant.ofEpochSecond(1677613970, 453204838))
                        .target("testmail@test.de")
                        .source("oldApp")
                        .build()))
                .build();
        store.put("userId", givenConsent);


        AdvertisementConsentGivenEvent advertisementConsentGivenEvent = AdvertisementConsentGivenEvent.builder()
                .customerId("userId")
                .target("testmail@test.de")
                .channel("mail")
                .time(Instant.ofEpochSecond(1677643971, 453205839))
                .source("newApp")
                .build();

        String event = mapper.writeValueAsString(advertisementConsentGivenEvent);

        inputTopic.pipeInput("userId", event);

        CustomerConsent expected = CustomerConsent.builder()
                .customerId("userId")
                .channelConsents(Set.of(ChannelConsent.builder()
                        .channel("mail")
                        .consentDate(Instant.ofEpochSecond(1677643971, 453205839))
                        .target("testmail@test.de")
                        .source("newApp")
                        .build()))
                .build();

        assertThat(store.get("userId")).isEqualTo(expected);
    }

}
