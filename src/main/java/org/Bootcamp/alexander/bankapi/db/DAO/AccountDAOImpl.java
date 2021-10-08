package org.Bootcamp.alexander.bankapi.db.DAO;

import org.Bootcamp.alexander.bankapi.db.H2JDBCUtils;
import org.Bootcamp.alexander.bankapi.exception.AccountNotFoundException;
import org.Bootcamp.alexander.bankapi.model.Account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @AccountDAOImpl - класс реализующий интерфейс AccountDAO.
 * Содержащий в себе методы для добавления средств  и получения баланса из базы данных.
 */

public class AccountDAOImpl implements AccountDAO{
    private final String addMoneyQuery = "UPDATE ACCOUNT SET BALANCE = (BALANCE + ?) " +
            "WHERE NUMBER = ?;";

    private final String getBalanceQuery = "SELECT BALANCE FROM ACCOUNT " +
            "WHERE NUMBER = ?;";

    private final String createAccountQuery = "INSERT INTO ACCOUNT (number, balance, client_id)"+" " +
            "VALUES ( ?, ?, ?);";

    private final String deleteAccountQuery = "DELETE FROM ACCOUNT WHERE client_id = ?";


    @Override
    public void createAccount(Account account) throws SQLException {
        try(Connection connection = H2JDBCUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement(createAccountQuery)){
            statement.setString(1, account.getNumber());
            statement.setString(2, account.getBalance().toString());
            statement.setString(3, account.getClientId()+"");
            statement.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
            //Logs....
        }
    }

    @Override
    public void deleteAccount(Account account) throws SQLException{
        try(Connection connection = H2JDBCUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteAccountQuery)){
            statement.setString(1, account.getClientId()+"");
            statement.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void addMoney(String number, BigDecimal sum) throws AccountNotFoundException {
        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(addMoneyQuery)) {

            statement.setBigDecimal(1, sum);
            statement.setString(2, number);
            int rowsChanged = statement.executeUpdate();
            if (rowsChanged == 0) {
                throw new AccountNotFoundException();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        BigDecimal balance = null;

        try (Connection connection = H2JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(getBalanceQuery)) {

            statement.setString(1, accountNumber);
            ResultSet rs = statement.executeQuery();
//            if (!rs.isBeforeFirst()) {
//                throw new AccountNotFoundException();
//            }
            if (rs.next()) {
                balance = rs.getBigDecimal("balance");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return balance;
    }
}
