package org.Bootcamp.alexander.bankapi.service;

import org.Bootcamp.alexander.bankapi.db.DAO.AccountDAO;
import org.Bootcamp.alexander.bankapi.exception.AccountNotFoundException;
import org.Bootcamp.alexander.bankapi.model.Deposit;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * @AccountServiceImpl - класс реализующий интерфейс AccountService.
 * Так же этот класс содержит в себе методы для пополнения баланса,
 * для получения баланса.
 *
 */

public class AccountServiceImpl implements AccountService {
    AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void topUpAccountBalance(Deposit deposit) throws AccountNotFoundException {
        accountDAO.addMoney(deposit.getAccountNumber(), deposit.getSum());
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) throws SQLException {
        return accountDAO.getBalance(accountNumber);
    }
}
