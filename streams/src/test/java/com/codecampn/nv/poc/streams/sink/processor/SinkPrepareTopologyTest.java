package com.codecampn.nv.poc.streams.sink.processor;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentUpdateEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.serdes.AdvertisementConsentUpdateEventSerde;
import com.codecampn.nv.poc.streams.sink.model.ConsentPreferenceKey;
import com.codecampn.nv.poc.streams.sink.model.ConsentPreferenceKeyPayload;
import com.codecampn.nv.poc.streams.sink.model.ConsentPreferenceValue;
import com.codecampn.nv.poc.streams.sink.serdes.ConsentPreferenceKeySerde;
import com.codecampn.nv.poc.streams.sink.serdes.ConsentPreferenceValueSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class SinkPrepareTopologyTest {

    private SinkPrepareProcessor sinkPrepareProcessor;

    private TopologyTestDriver topologyTestDriver;

    private static final Serde<String> STRING_SERDE = Serdes.String();

    private TestInputTopic<String, AdvertisementConsentUpdateEvent> inputTopic;

    private TestOutputTopic<ConsentPreferenceKey, ConsentPreferenceValue> outputTopic;

    @BeforeEach
    void setUp() {
        sinkPrepareProcessor = new SinkPrepareProcessor();

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        sinkPrepareProcessor.buildPipeline(streamsBuilder);
        Topology topology = streamsBuilder.build();
        this.topologyTestDriver = new TopologyTestDriver(topology, new Properties());
        this.inputTopic = topologyTestDriver.createInputTopic(
                "consent.preference",
                STRING_SERDE.serializer(),
                AdvertisementConsentUpdateEventSerde.Instance.serializer());
        this.outputTopic = topologyTestDriver.createOutputTopic(
                "consent.preference.sink",
                ConsentPreferenceKeySerde.Instance.deserializer(),
                ConsentPreferenceValueSerde.Instance.deserializer());
    }

    @Test
    void itShouldAddSchemaToOutputTopic() {
        AdvertisementConsentUpdateEvent event = AdvertisementConsentUpdateEvent.builder()
                .type("consent/AdvertisementConsentGivenEvent")
                .channel("mail")
                .target("testmail@test.de")
                .time(Instant.now())
                .customerId("userId")
                .build();

        inputTopic.pipeInput("userId", event);

        ConsentPreferenceKey consentPreferenceKey = new ConsentPreferenceKey(new ConsentPreferenceKeyPayload("userId", "testmail@test.de"));
        ConsentPreferenceValue consentPreferenceValue = new ConsentPreferenceValue(event);

        var actual = outputTopic.readKeyValuesToList();
        assertThat(actual)
                .containsExactly(
                        KeyValue.pair(consentPreferenceKey, consentPreferenceValue)
                );
    }

    @Test
    void outputTopicShouldHaveANullValue_whenAdvertisementConsentRevokedEventIsThrown() {

        AdvertisementConsentUpdateEvent event = AdvertisementConsentUpdateEvent.builder()
                .type("consent/AdvertisementConsentRevokedEvent")
                .channel("mail")
                .target("testmail@test.de")
                .time(Instant.now())
                .customerId("userId")
                .build();

        inputTopic.pipeInput("userId", event);

        ConsentPreferenceKey consentPreferenceKey = new ConsentPreferenceKey(new ConsentPreferenceKeyPayload("userId", "testmail@test.de"));

        var actual = outputTopic.readKeyValuesToList();
        assertThat(actual).containsExactly(KeyValue.pair(consentPreferenceKey, null));
    }

}
