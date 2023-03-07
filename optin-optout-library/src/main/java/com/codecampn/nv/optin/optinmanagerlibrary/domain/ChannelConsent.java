package com.codecampn.nv.optin.optinmanagerlibrary.domain;

import lombok.*;

import java.time.Instant;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public class ChannelConsent {
    private String channel;
    private Instant consentDate;
    private String source;

    /* the email-address or phone-number */
    private String target;

    ChannelConsent copy() {
        return toBuilder().build();
    }
}
