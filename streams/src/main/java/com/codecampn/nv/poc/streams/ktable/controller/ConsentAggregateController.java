package com.codecampn.nv.poc.streams.ktable.controller;

import com.codecampn.nv.optin.optinmanagerlibrary.domain.CustomerConsent;
import com.codecampn.nv.poc.streams.ktable.service.ConsentAggregateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer-consents")
public class ConsentAggregateController {

    private final ConsentAggregateService consentAggregateService;

    @Autowired
    public ConsentAggregateController(ConsentAggregateService consentAggregateService) {
        this.consentAggregateService = consentAggregateService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<CustomerConsent>> getAggregate() {
        var aggregate = consentAggregateService.getAggregate();

        return ResponseEntity.ok(aggregate);
    }


    @GetMapping(value = "/{userId}", produces = "application/json")
    public ResponseEntity<CustomerConsent> getConsent(@PathVariable("userId") String userId) {
        var aggregate = consentAggregateService.getConsent(userId);

        return ResponseEntity.ok(aggregate);
    }

}
