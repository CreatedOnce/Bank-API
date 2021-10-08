package org.Bootcamp.alexander.bankapi.service;

import org.Bootcamp.alexander.bankapi.exception.AccountNotFoundException;
import org.Bootcamp.alexander.bankapi.model.Deposit;
import org.Bootcamp.alexander.bankapi.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface AccountService {

    void insertAccountInDataBase(Account account) throws SQLException;
    void deleteAccountFromDataBase(Account account) throws SQLException;
    void topUpAccountBalance(Deposit deposit) throws AccountNotFoundException;
    BigDecimal getAccountBalance(String accountNumber) throws SQLException;

}
