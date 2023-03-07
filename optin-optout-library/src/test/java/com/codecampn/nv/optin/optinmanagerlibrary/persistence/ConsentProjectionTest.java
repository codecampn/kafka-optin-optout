package com.codecampn.nv.optin.optinmanagerlibrary.persistence;

import com.codecampn.nv.optin.optinmanagerlibrary.domain.ChannelConsent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentGivenEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentRevokedEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentMessageConsumer;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsentProjectionTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    ConsentMessageConsumer consentMessageConsumer;
    ConsentProjection consentProjection;

    AdvertisementConsentGivenEvent giveConsentEvent = AdvertisementConsentGivenEvent.builder()
            .customerId("customerId")
            .channel("channel")
            .source("source")
            .build();

    AdvertisementConsentRevokedEvent revokeConsentEvent = AdvertisementConsentRevokedEvent.builder()
            .customerId("customerId")
            .channel("channel")
            .source("source")
            .build();

    @BeforeEach
    public void setup() {
        consentProjection = new ConsentProjection(customerRepository, consentMessageConsumer);
    }

    @Test
    @DisplayName("should add consents for advertisementConsentGivenEvents")
    public void shouldAddConsentsForAdvertisementConsentGivenEvents() {

        Mockito.when(consentMessageConsumer.consume(Mockito.anyString()))
                .thenAnswer(invocation -> Flowable.fromArray(giveConsentEvent));

        consentProjection.fromOffset(0);

        ChannelConsent expectedConsent = ChannelConsent.builder()
                .channel("channel")
                .source("source")
                .consentDate(giveConsentEvent.getTime())
                .build();
        Mockito.verify(customerRepository).addConsent(Mockito.eq("customerId"), Mockito.eq(expectedConsent));
    }

    @Test
    @DisplayName("should revoke consents for advertisementConsentRevokedEvents")
    public void shouldRevokeConsentsForAdvertisementConsentRevokedEvents() {
        Mockito.when(consentMessageConsumer.consume(Mockito.anyString()))
                .thenAnswer(invocation -> Flowable.fromArray(revokeConsentEvent));

        consentProjection.fromOffset(0);

        Mockito.verify(customerRepository).revokeConsent(Mockito.eq("customerId"), Mockito.eq("channel"));
    }

}