package org.Bootcamp.alexander.bankapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final @JsonProperty("id") int id;
    private final String number;
    private BigDecimal balance;
    private int clientId;

    public Account(@JsonProperty("number") String number,
                   @JsonProperty("balance") BigDecimal balance,
                   @JsonProperty("client_id") int clientId) {
        this.number = number;
        this.balance = balance;
        this.clientId = clientId;
        this.id = count.incrementAndGet();
    }
}
