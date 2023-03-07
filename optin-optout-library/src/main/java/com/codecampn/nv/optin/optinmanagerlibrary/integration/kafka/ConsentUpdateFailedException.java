package com.codecampn.nv.optin.optinmanagerlibrary.integration.kafka;

public class ConsentUpdateFailedException extends Exception {

    public ConsentUpdateFailedException(String message) {
        super(message);
    }

    public ConsentUpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
