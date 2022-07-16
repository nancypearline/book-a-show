package com.nancy.booking.show.model;

import java.io.Serializable;

public enum Role implements Serializable {

    ADMIN("ADMIN"),

    BUYER("BUYER");

    private final String value;

    Role(String role) {
        this.value = role;
    }

    public String getValue() {
        return value;
    }
}
