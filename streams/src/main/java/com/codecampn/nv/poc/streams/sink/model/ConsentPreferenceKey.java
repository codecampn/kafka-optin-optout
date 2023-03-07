package com.codecampn.nv.poc.streams.sink.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ConsentPreferenceKey {
    private final ConsentPreferenceKeyPayload payload;
    private final ConsentPreferenceKeySchema schema = new ConsentPreferenceKeySchema();

    @JsonCreator
    public ConsentPreferenceKey(@JsonProperty("payload") ConsentPreferenceKeyPayload payload) {
        this.payload = payload;
    }
}
