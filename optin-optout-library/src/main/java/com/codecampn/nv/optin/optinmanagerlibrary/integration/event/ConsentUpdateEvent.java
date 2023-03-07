package com.codecampn.nv.optin.optinmanagerlibrary.integration.event;

/**
 * Marker interface for domain objects that change the users consent.
 */
public interface ConsentUpdateEvent {

    String getCustomerId();
}
