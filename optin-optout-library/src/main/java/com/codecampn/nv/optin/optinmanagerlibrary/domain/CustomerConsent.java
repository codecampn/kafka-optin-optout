package com.codecampn.nv.optin.optinmanagerlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomerConsent {
    private String customerId;

    @Builder.Default
    private Set<ChannelConsent> channelConsents = new HashSet<>();

    public CustomerConsent copy() {
        return toBuilder().channelConsents(channelConsents.stream()
                .map(ChannelConsent::copy)
                .collect(Collectors.toSet())).build();
    }
}
