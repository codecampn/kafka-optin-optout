package com.codecampn.nv.optin.optinmanagerlibrary.service;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.GiveAdvertismentConsentCommand;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.CommandDispatcher;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.RevokeAdvertismentConsentCommand;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentUpdateFailedException;

import java.time.Instant;

public class ConsentService {
    private final CommandDispatcher commandDispatcher;
    private final String source;

    public ConsentService(CommandDispatcher commandDispatcher, String source) {
        this.commandDispatcher = commandDispatcher;
        this.source = source;
    }


    public void optInForChannel(String customer, String channel, String target) throws ConsentUpdateFailedException {
        optInForChannel(customer, channel, target, Instant.now());
    }

    public void optInForChannel(String customer, String channel, String target, Instant time) throws ConsentUpdateFailedException {
        var command = GiveAdvertismentConsentCommand.builder()
                .source(source)
                .target(target)
                .channel(channel)
                .time(time)
                .customerId(customer)
                .build();
        commandDispatcher.dispatch(command);
    }

    public void optOutForChannel(String customer, String channel, String target) throws ConsentUpdateFailedException {
        optOutForChannel(customer, channel, target, Instant.now());
    }

    public void optOutForChannel(String customer, String channel, String target, Instant time) throws ConsentUpdateFailedException {
        var command = RevokeAdvertismentConsentCommand.builder()
                .source(source)
                .target(target)
                .channel(channel)
                .time(time)
                .customerId(customer)
                .build();
        commandDispatcher.dispatch(command);
    }

}
