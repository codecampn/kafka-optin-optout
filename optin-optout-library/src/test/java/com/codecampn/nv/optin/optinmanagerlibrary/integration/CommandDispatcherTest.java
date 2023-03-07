package com.codecampn.nv.optin.optinmanagerlibrary.integration;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.GiveAdvertismentConsentCommand;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.RevokeAdvertismentConsentCommand;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentGivenEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentRevokedEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentMessageProducer;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentUpdateFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommandDispatcherTest {

    private CommandDispatcher commandDispatcher;

    @Mock
    ConsentMessageProducer consentMessageProducer;

    @BeforeEach
    public void setup() {
        this.commandDispatcher = new CommandDispatcher(consentMessageProducer);
    }

    @Test
    @DisplayName("Should dispatch an AdvertismentConsentGivenEvent on GiveAdvertismentConsentCommands")
    public void dispatch_shouldDispatchAdvertismentConsentGivenEvents() throws ConsentUpdateFailedException {
        var command = GiveAdvertismentConsentCommand.builder()
                .customerId("customer")
                .channel("channel")
                .source("source")
                .build();
        var expectedEvent = AdvertisementConsentGivenEvent.builder()
                .customerId("customer")
                .channel("channel")
                .time(command.getTime())
                .source("source")
                .build();

        commandDispatcher.dispatch(command);

        Mockito.verify(consentMessageProducer).produceConsentUpdate(Mockito.eq(expectedEvent));
    }

    @Test
    @DisplayName("Should dispatch an AdvertismentConsentRevokedEvent on RevokeAdvertismentConsentCommands")
    public void dispatch_shouldDispatchAdvertismentConsentRevokeEvents() throws ConsentUpdateFailedException {
        var command = RevokeAdvertismentConsentCommand.builder()
                .customerId("customer")
                .channel("channel")
                .source("source")
                .build();
        var expectedEvent = AdvertisementConsentRevokedEvent.builder()
                .customerId("customer")
                .channel("channel")
                .time(command.getTime())
                .source("source")
                .build();

        commandDispatcher.dispatch(command);

        Mockito.verify(consentMessageProducer).produceConsentUpdate(Mockito.eq(expectedEvent));
    }
}