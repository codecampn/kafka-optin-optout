package com.codecampn.nv.poc.streams.ktable.service;

import com.codecampn.nv.optin.optinmanagerlibrary.domain.CustomerConsent;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsentAggregateService {

    private final StreamsBuilderFactoryBean factoryBean;


    public ConsentAggregateService(@Qualifier("tableApp") StreamsBuilderFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    public CustomerConsent getConsent(String userId) {
        return getStore().get(userId);
    }

    public List<CustomerConsent> getAggregate() {
        var aggregate = getStore().all();
        List<CustomerConsent> result = new ArrayList<>();
        aggregate.forEachRemaining(item -> {
            result.add(item.value);
        });
        return result;

    }

    private ReadOnlyKeyValueStore<String, CustomerConsent> getStore() {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        return kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(
                        "consent.preference.store",
                        QueryableStoreTypes.keyValueStore()));
    }
}
