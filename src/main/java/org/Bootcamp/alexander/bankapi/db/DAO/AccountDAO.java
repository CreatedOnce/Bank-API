package org.Bootcamp.alexander.bankapi.db.DAO;

import org.Bootcamp.alexander.bankapi.exception.AccountNotFoundException;
import org.Bootcamp.alexander.bankapi.model.Account;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface AccountDAO {
    void createAccount(Account account) throws SQLException;
    void deleteAccount(Account account) throws SQLException;
    void addMoney(String number, BigDecimal sum) throws AccountNotFoundException;
    BigDecimal getBalance(String accountNumber) throws SQLException;
}
