package com.sample.store.servicesgateway.config;

public class AuthBean {
    private String message;

    public AuthBean(String you_are_authenticated) {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
