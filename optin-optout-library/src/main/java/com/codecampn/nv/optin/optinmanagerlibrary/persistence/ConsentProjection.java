package com.codecampn.nv.optin.optinmanagerlibrary.persistence;

import com.codecampn.nv.optin.optinmanagerlibrary.domain.ChannelConsent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentGivenEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentRevokedEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentMessageConsumer;

public class ConsentProjection {

    private final CustomerRepository repository;
    private final ConsentMessageConsumer consumer;

    public ConsentProjection(CustomerRepository customerRepository, ConsentMessageConsumer consumer) {
        this.repository = customerRepository;
        this.consumer = consumer;
    }

    public void fromOffset(int offset) {
        consumer.consume("consent.preference").subscribe(event -> {
            if (event instanceof AdvertisementConsentGivenEvent ev) {
                reduce(ev);
            } else if (event instanceof AdvertisementConsentRevokedEvent ev) {
                reduce(ev);
            }
        });
    }

    private void reduce(AdvertisementConsentGivenEvent event) {
        repository.addConsent(event.getCustomerId(),
                ChannelConsent.builder()
                        .source(event.getSource())
                        .channel(event.getChannel())
                        .consentDate(event.getTime())
                        .build());
    }

    private void reduce(AdvertisementConsentRevokedEvent event) {
        repository.revokeConsent(event.getCustomerId(), event.getChannel());
    }
}
