package com.codecampn.nv.poc.streams.sink.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Field {
    private String type;
    private Boolean optional;
    private String field;
}
