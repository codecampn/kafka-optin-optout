package com.codecampn.nv.poc.optin.service;

import com.codecampn.nv.optin.optinmanagerlibrary.ConsentManager;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentUpdateFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;


@Service
public class ConsentService {
    private final ConsentManager consentManager;

    @Autowired
    public ConsentService(ConsentManager consentManager) {
        this.consentManager = consentManager;
    }

    @PostConstruct
    public void startEventSourcing() {
        this.consentManager.start();
    }


    public void grantConsent(String consentId, String channel, String target) throws ConsentUpdateFailedException {
        consentManager.getConsentService().optInForChannel(consentId, channel, target);
    }

    public void revokeConsent(String consentId, String channel, String target) throws ConsentUpdateFailedException {
        consentManager.getConsentService().optOutForChannel(consentId, channel, target);
    }
}
