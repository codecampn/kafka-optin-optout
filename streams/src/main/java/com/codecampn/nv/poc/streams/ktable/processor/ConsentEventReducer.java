package com.codecampn.nv.poc.streams.ktable.processor;
import com.codecampn.nv.optin.optinmanagerlibrary.domain.ChannelConsent;
import com.codecampn.nv.optin.optinmanagerlibrary.domain.CustomerConsent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentGivenEvent;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.event.AdvertisementConsentRevokedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class ConsentEventReducer {

    protected final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    public CustomerConsent reduce(String key, String value, CustomerConsent aggregate) {
        try {
            var eventType = mapper.readTree(value).get("type").asText();

            if (AdvertisementConsentGivenEvent.EVENT_TYPE.equals(eventType)) {
                return handleEvent(mapper.readValue(value, AdvertisementConsentGivenEvent.class), aggregate);
            }
            if (AdvertisementConsentRevokedEvent.EVENT_TYPE.equals(eventType)) {
                return handleEvent(mapper.readValue(value, AdvertisementConsentRevokedEvent.class), aggregate);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return aggregate;
    }

    private CustomerConsent handleEvent(AdvertisementConsentGivenEvent event, CustomerConsent source) {
        source.setCustomerId(event.getCustomerId());
        source.getChannelConsents().removeIf(consent -> consent.getTarget().equals(event.getTarget()));
        ChannelConsent channelConsent = ChannelConsent.builder()
                .source(event.getSource())
                .consentDate(event.getTime())
                .channel(event.getChannel())
                .target(event.getTarget())
                .build();
        source.getChannelConsents().add(channelConsent);
        return source;
    }

    private CustomerConsent handleEvent(AdvertisementConsentRevokedEvent event, CustomerConsent source) {

        source.getChannelConsents().removeIf(channelConsent -> channelConsent.getTarget().equals(event.getTarget()));

        return source;
    }
}
