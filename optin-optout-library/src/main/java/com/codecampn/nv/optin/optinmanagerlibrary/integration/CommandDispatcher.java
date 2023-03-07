package com.codecampn.nv.optin.optinmanagerlibrary.integration;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.Command;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.GiveAdvertismentConsentCommand;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.command.RevokeAdvertismentConsentCommand;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentGivenEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentRevokedEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentMessageProducer;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentUpdateFailedException;

public class CommandDispatcher {

    private final ConsentMessageProducer consentMessageProducer;

    public CommandDispatcher(ConsentMessageProducer consentMessageProducer) {
        this.consentMessageProducer = consentMessageProducer;
    }

    public void dispatch(Command command) throws ConsentUpdateFailedException {
        if (command instanceof GiveAdvertismentConsentCommand cmd) {
            consentMessageProducer.produceConsentUpdate(AdvertisementConsentGivenEvent.fromCommand(cmd));
        } else if (command instanceof RevokeAdvertismentConsentCommand cmd) {
            consentMessageProducer.produceConsentUpdate(AdvertisementConsentRevokedEvent.fromCommand(cmd));
        } else {
            throw new IllegalStateException("Unexpected value: " + command);
        }
    }
}
