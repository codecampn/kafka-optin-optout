package com.codecampn.nv.poc.streams.sink.model;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentUpdateEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ConsentPreferenceValue {
    private final AdvertisementConsentUpdateEvent payload;
    private final ConsentPreferenceValueSchema schema;

    @JsonCreator
    public ConsentPreferenceValue(@JsonProperty("payload") AdvertisementConsentUpdateEvent payload) {
        this.payload = payload;
        this.schema = new ConsentPreferenceValueSchema();
    }

}
