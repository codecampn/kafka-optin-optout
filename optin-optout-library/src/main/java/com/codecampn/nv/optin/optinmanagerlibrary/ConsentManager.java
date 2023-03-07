package com.codecampn.nv.optin.optinmanagerlibrary;

import com.codecampn.nv.optin.optinmanagerlibrary.integration.CommandDispatcher;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentMessageConsumer;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.ConsentMessageProducer;
import com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka.KafkaClientFactory;
import com.codecampn.nv.optin.optinmanagerlibrary.persistence.ConsentProjection;
import com.codecampn.nv.optin.optinmanagerlibrary.persistence.CustomerRepository;
import com.codecampn.nv.optin.optinmanagerlibrary.service.ConsentService;

import java.util.Properties;

public class ConsentManager {


    ConsentService consentService;
    ConsentProjection consentProjection;
    CustomerRepository customerRepository = new CustomerRepository();
    ConsentMessageConsumer consentMessageConsumer ;

    ConsentMessageProducer consentMessageProducer ;
    CommandDispatcher commandDispatcher;

    public ConsentManager(String app, Properties producerProperties, Properties consumerProperties) {
        consentMessageConsumer = new ConsentMessageConsumer(KafkaClientFactory.createKafkaConsumer(consumerProperties));
        consentProjection = new ConsentProjection(customerRepository, consentMessageConsumer);
        consentMessageProducer = new ConsentMessageProducer(KafkaClientFactory.createKafkaProducer(producerProperties));
        commandDispatcher = new CommandDispatcher(consentMessageProducer);
        consentService = new ConsentService(commandDispatcher, app);
    }



    public void start() {
       consentProjection.fromOffset(0);
    }

    public ConsentService getConsentService() {
        return consentService;
    }
}
