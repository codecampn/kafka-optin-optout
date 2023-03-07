package com.codecampn.nv.optin.optinmanagerlibrary.persistence;

import com.codecampn.nv.optin.optinmanagerlibrary.domain.ChannelConsent;
import com.codecampn.nv.optin.optinmanagerlibrary.domain.CustomerConsent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    CustomerRepository customerRepository = new CustomerRepository();

    @Test
    @DisplayName("should add a customer when a consent is given")
    public void shouldAddCustomerWhenConsentGiven() {
        var consentDate = Instant.now();
        ChannelConsent consent = ChannelConsent.builder()
                .channel("channel")
                .source("source")
                .consentDate(consentDate)
                .build();

        customerRepository.addConsent("customerId", consent);

        //noinspection OptionalGetWithoutIsPresent
        Optional<CustomerConsent> customerFromRepo = customerRepository.findCustomerById("customerId");

        assertTrue(customerFromRepo.isPresent());
        assertEquals(customerFromRepo.get().getCustomerId(), "customerId");
        assertEquals(customerFromRepo.get().getChannelConsents().size(), 1);
        assertTrue(customerFromRepo.get().getChannelConsents().contains(consent));
    }

    @Test
    @DisplayName("should remove a customer consente when a consent is revoked")
    public void shouldRevokeCustomerConsentsOnRevoke() {
        var consentDate = Instant.now();
        ChannelConsent consent = ChannelConsent.builder()
                .channel("channel")
                .source("source")
                .consentDate(consentDate)
                .build();

        customerRepository.addConsent("customerId", consent);
        customerRepository.revokeConsent("customerId", "channel");

        //noinspection OptionalGetWithoutIsPresent
        Optional<CustomerConsent> customerFromRepo = customerRepository.findCustomerById("customerId");

        assertTrue(customerFromRepo.isPresent());
        assertEquals(customerFromRepo.get().getCustomerId(), "customerId");
        assertEquals(customerFromRepo.get().getChannelConsents().size(), 0);
    }
}