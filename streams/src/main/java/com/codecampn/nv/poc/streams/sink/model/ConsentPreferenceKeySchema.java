package com.codecampn.nv.poc.streams.sink.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
public class ConsentPreferenceKeySchema {
    private final String type = "struct";
    private final List<Field> fields = new ArrayList<>();

    public ConsentPreferenceKeySchema() {
        this.fields.add(new Field("string", false, "customerId"));
        this.fields.add(new Field("string", false, "target"));
    }
}
