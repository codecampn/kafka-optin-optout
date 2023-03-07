package com.codecampn.nv.poc.streams.sink.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class ConsentPreferenceKeyPayload {
    private String customerId;
    private String target;
}
