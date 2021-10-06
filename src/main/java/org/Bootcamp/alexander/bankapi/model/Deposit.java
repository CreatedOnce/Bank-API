package org.Bootcamp.alexander.bankapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Deposit {
    private final String accountNumber;
    private final BigDecimal sum;

    public Deposit(@JsonProperty("number") String accountNumber,
                   @JsonProperty("sum") BigDecimal sum) {
        this.accountNumber = accountNumber;
        this.sum = sum;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getSum() {
        return sum;
    }
}
