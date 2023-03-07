package com.codecampn.nv.optin.optinmanagerlibrary.integration.event;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.RevokeAdvertismentConsentCommand;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertisementConsentRevokedEvent implements ConsentUpdateEvent, Event {
    public static final String EVENT_TYPE = "consent/AdvertisementConsentRevokedEvent";

    private final String type = "consent/AdvertisementConsentRevokedEvent";
    @EqualsAndHashCode.Exclude
    private final Instant eventTime = Instant.now();

    private String customerId;
    private String target;

    private String channel;
    @Builder.Default
    private Instant time = Instant.now();
    private String source;

    public static AdvertisementConsentRevokedEvent fromCommand(RevokeAdvertismentConsentCommand command) {
        return AdvertisementConsentRevokedEvent.builder()
                .customerId(command.getCustomerId())
                .channel(command.getChannel())
                .time(command.getTime())
                .target(command.getTarget())
                .source(command.getSource())
                .build();
    }
}
