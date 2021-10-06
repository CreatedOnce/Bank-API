package org.Bootcamp.alexander.bankapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private final @JsonProperty("id") int id;
    private String firstName;
    private String lastName;

    public Client(@JsonProperty("first_name") String firstName,
                  @JsonProperty("last_name") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = counter.incrementAndGet();
    }
}

