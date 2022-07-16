package com.nancy.booking.show.model;

import java.io.Serializable;

public enum Movie implements Serializable {

    SPIDERMAN ("SPIDERMAN"),
    EXCUSE_ME_BROTHER ("EXCUSE_ME_BROTHER"),
    MOTHERING_SUNDAY ("MOTHERING_SUNDAY"),
    PONNIYIN_SELVAN ("PONNIYIN_SELVAN");

    private final String value;

    Movie(String movieName) {
        this.value = movieName;
    }

    public String getValue() {
        return value;
    }
}


