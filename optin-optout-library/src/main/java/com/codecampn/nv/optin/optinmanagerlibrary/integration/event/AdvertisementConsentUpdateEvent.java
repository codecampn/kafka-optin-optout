package com.codecampn.nv.optin.optinmanagerlibrary.integration.event;

import lombok.*;
import java.time.Instant;

@Builder
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementConsentUpdateEvent implements ConsentUpdateEvent, Event {

    private String type;
    @EqualsAndHashCode.Exclude
    private final Instant eventTime = Instant.now();

    private String customerId;
    private String target;

    private String channel;
    @Builder.Default
    private Instant time = Instant.now();
    private String source;

}
