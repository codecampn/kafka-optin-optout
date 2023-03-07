package com.codecampn.nv.poc.streams.sink.processor;

import com.codecampn.nv.optin.optinmanagerlibrary.serdes.AdvertisementConsentUpdateEventSerde;
import com.codecampn.nv.poc.streams.sink.model.ConsentPreferenceKey;
import com.codecampn.nv.poc.streams.sink.model.ConsentPreferenceKeyPayload;
import com.codecampn.nv.poc.streams.sink.model.ConsentPreferenceValue;
import com.codecampn.nv.poc.streams.sink.serdes.ConsentPreferenceKeySerde;
import com.codecampn.nv.poc.streams.sink.serdes.ConsentPreferenceValueSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class SinkPrepareProcessor {
    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    void buildPipeline(@Qualifier("sinkApp") StreamsBuilder streamsBuilder) {
        streamsBuilder
                .stream("consent.preference", Consumed.with(
                        STRING_SERDE,
                        AdvertisementConsentUpdateEventSerde.Instance
                ))
                .map(((key, value) -> {
                    ConsentPreferenceKeyPayload newKey = new ConsentPreferenceKeyPayload(key, value.getTarget());
                    System.out.print("Hieeeer");
                    System.out.print(value);
                    if(value.getType().equals("consent/AdvertisementConsentGivenEvent")) {

                        System.out.print(value);
                        return new KeyValue<>(
                                new ConsentPreferenceKey(newKey),
                                new ConsentPreferenceValue(value));
                    } else {
                        return new KeyValue<>(
                                new ConsentPreferenceKey(newKey),
                                null);
                    }
                }))
                .to("consent.preference.sink",
                        Produced.with(ConsentPreferenceKeySerde.Instance,
                                ConsentPreferenceValueSerde.Instance)
                );
    }
}
