package com.codecampn.nv.poc.optin.api;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentUpdateFailedException;
import com.codecampn.nv.poc.optin.api.model.Customer;
import com.codecampn.nv.poc.optin.api.model.GrantConsentRequest;
import com.codecampn.nv.poc.optin.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class CustomerApiDelegateImpl implements CustomerApiDelegate {

    @Autowired
    private ConsentService consentService;


    @Override
    public Mono<ResponseEntity<Void>> grantConsent(String customerId, String channel, Mono<GrantConsentRequest> body, ServerWebExchange exchange) {
        return body.flatMap(b -> {
            try {
                consentService.grantConsent(customerId, channel, b.getTarget());
            } catch (ConsentUpdateFailedException e) {
                return Mono.error(e);
            }
            return Mono.just(ResponseEntity.ok().build());
        });
    }


    @Override
    public Mono<ResponseEntity<Void>> revokeConsent(String customerId, String channel, Mono<GrantConsentRequest> body, ServerWebExchange exchange) {
        return body.flatMap(b -> {
            try {
                consentService.revokeConsent(customerId, channel, b.getTarget());
            } catch (ConsentUpdateFailedException e) {
                return Mono.error(e);
            }
            return Mono.just(ResponseEntity.ok().build());
        });
    }

}
