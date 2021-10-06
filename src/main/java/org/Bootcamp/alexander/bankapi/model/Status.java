package org.Bootcamp.alexander.bankapi.model;

public enum Status {
    PENDING(0), ACTIVE(1), CLOSED(2);

    private Status(int value) {
        this.value = value;
    }

    private final int value;
}
