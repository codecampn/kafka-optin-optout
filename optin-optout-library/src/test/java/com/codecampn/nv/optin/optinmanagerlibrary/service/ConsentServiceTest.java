package com.codecampn.nv.optin.optinmanagerlibrary.service;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.GiveAdvertismentConsentCommand;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.CommandDispatcher;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentUpdateFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

@ExtendWith(MockitoExtension.class)
class ConsentServiceTest {

    ConsentService consentService;

    @Mock
    CommandDispatcher commandDispatcher;

    @BeforeEach
    public void setup() {
        consentService = new ConsentService(commandDispatcher, "mockSource");
    }

    @Test
    @DisplayName("Should dispatch an GiveAdvertismentConsentCommand on optIn")
    public void optInShouldDispatchAnAdvertismentCommand() throws ConsentUpdateFailedException {
        var eventTime = Instant.now();
        consentService.optInForChannel("customerId", "channel2", "mail@test", eventTime);

        var expectedCommand = GiveAdvertismentConsentCommand.builder()
                .source("mockSource")
                .channel("channel2")
                .target("mail@test")
                .customerId("customerId")
                .time(eventTime)
                .build();

        Mockito.verify(commandDispatcher).dispatch(Mockito.eq(expectedCommand));
    }
}