package org.Bootcamp.alexander.bankapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class Card {
    private static final AtomicInteger counter = new AtomicInteger(0);
    public final @JsonProperty("id") int id;
    private final String number;
    private final String month;
    private final String year;
    private final String code;
    private BigDecimal balance;
    private @JsonProperty("status") Status status; // [ждет активации, активна, закрыта]
    private int accountId;

    public Card(@JsonProperty("number") String number,
                @JsonProperty("month") String month,
                @JsonProperty("year") String year,
                @JsonProperty("code") String code,
                @JsonProperty("balance") BigDecimal balance,
                @JsonProperty("account_id") int accountId) {
        this.number = number;
        this.month = month;
        this.year = year;
        this.code = code;
        this.balance = balance;
        this.status = Status.PENDING;
        this.accountId = accountId;
        this.id = counter.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Status getStatus() {
        return status;
    }

    public int getAccountId() {
        return accountId;
    }
}
