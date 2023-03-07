package com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka;

public class EventProcessingFailedException extends RuntimeException {
    public EventProcessingFailedException(String message) {
        super(message);
    }

    public EventProcessingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
