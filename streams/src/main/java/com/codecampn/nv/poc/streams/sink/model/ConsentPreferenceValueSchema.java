package com.codecampn.nv.poc.streams.sink.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class ConsentPreferenceValueSchema {
    private final String type = "struct";

    private final List<Field> fields = new ArrayList<>();

    public ConsentPreferenceValueSchema() {
        this.fields.add(new Field("string", false, "type"));
        this.fields.add(new Field("int32", false, "eventTime"));
        this.fields.add(new Field("string", false, "customerId"));
        this.fields.add(new Field("string", false, "channel"));
        this.fields.add(new Field("string", false, "target"));
        this.fields.add(new Field("string", false, "source"));
        this.fields.add(new Field("int32", false, "time"));
    }
}
