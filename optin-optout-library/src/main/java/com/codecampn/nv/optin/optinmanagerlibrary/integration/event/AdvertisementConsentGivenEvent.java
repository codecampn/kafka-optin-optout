package com.codecampn.nv.optin.optinmanagerlibrary.integration.event;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.GiveAdvertismentConsentCommand;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class AdvertisementConsentGivenEvent implements ConsentUpdateEvent, Event {
    public static final String EVENT_TYPE = "consent/AdvertisementConsentGivenEvent";

    private final String type = EVENT_TYPE;
    @EqualsAndHashCode.Exclude
    private final Instant eventTime = Instant.now();

    private String customerId;
    private String target;

    private String channel;
    @Builder.Default
    private Instant time = Instant.now();
    private String source;


    public static AdvertisementConsentGivenEvent fromCommand(GiveAdvertismentConsentCommand command) {
        return AdvertisementConsentGivenEvent.builder()
                .customerId(command.getCustomerId())
                .target(command.getTarget())
                .channel(command.getChannel())
                .time(command.getTime())
                .source(command.getSource())
                .build();
    }
}
