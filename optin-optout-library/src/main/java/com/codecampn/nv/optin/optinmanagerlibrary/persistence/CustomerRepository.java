package com.codecampn.nv.optin.optinmanagerlibrary.persistence;


import com.codecampn.nv.optin.optinmanagerlibrary.domain.ChannelConsent;
import com.codecampn.nv.optin.optinmanagerlibrary.domain.CustomerConsent;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Inmemory customer repository
 */
public class CustomerRepository {

    ConcurrentHashMap<String, CustomerConsent> customers = new ConcurrentHashMap<>();

    public Optional<CustomerConsent> findCustomerById(String id) {
        return Optional.ofNullable(customers.get(id).copy());
    }


    public void addConsent(String customerId, ChannelConsent consent) {
        CustomerConsent customerConsent = customers.get(customerId);
        if (customerConsent == null) {
            customerConsent = CustomerConsent.builder().customerId(customerId).build();
        }
        CustomerConsent updatedConsent = customerConsent.copy();
        updatedConsent.getChannelConsents().add(consent);
        customers.put(customerId, updatedConsent);
    }

    public void revokeConsent(String customerId, String channel) {
        CustomerConsent customerConsent = customers.get(customerId);
        if (customerConsent == null) {
            return;
        }
        CustomerConsent updatedConsent = customerConsent.copy();
        updatedConsent.getChannelConsents().removeIf(consent -> consent.getChannel().equals(channel));
        customers.put(customerId, updatedConsent);
    }
}
