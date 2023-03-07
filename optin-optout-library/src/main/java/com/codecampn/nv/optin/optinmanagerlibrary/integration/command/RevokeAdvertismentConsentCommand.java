package com.codecampn.nv.optin.optinmanagerlibrary.integration.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevokeAdvertismentConsentCommand implements ConsentUpdateCommand, Command {
    private String customerId;
    private String channel;
    private String target;
    @Builder.Default
    private Instant time = Instant.now();
    private String source;
}
