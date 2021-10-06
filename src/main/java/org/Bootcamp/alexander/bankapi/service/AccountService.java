package org.Bootcamp.alexander.bankapi.service;

import org.Bootcamp.alexander.bankapi.exception.AccountNotFoundException;
import org.Bootcamp.alexander.bankapi.model.Deposit;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface AccountService {

    void topUpAccountBalance(Deposit deposit) throws AccountNotFoundException;
    BigDecimal getAccountBalance(String accountNumber) throws SQLException;
}
