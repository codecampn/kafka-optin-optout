package com.codecampn.nv.poc.streams.ktable.processor;

import com.codecampn.nv.optin.optinmanagerlibrary.domain.CustomerConsent;
import com.codecampn.nv.poc.streams.ktable.serdes.CustomerConsentSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ConsentAggregateProcessor {
    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    public void buildPipeline(@Qualifier("tableApp") StreamsBuilder streamsBuilder) {
        ConsentEventReducer consentEventReducer = new ConsentEventReducer();

        streamsBuilder.stream("consent.preference", Consumed.with(STRING_SERDE, STRING_SERDE))
                .groupByKey()
                .aggregate(
                        () -> CustomerConsent.builder().build(),
                        consentEventReducer::reduce,
                        Materialized.<String, CustomerConsent, KeyValueStore<Bytes, byte[]>>as("consent.preference.store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(CustomerConsentSerde.Instance)
                ).toStream().print(Printed.<String, CustomerConsent>toSysOut().withLabel("Customer Aggregate"));
    }
}
