package org.Bootcamp.alexander.bankapi.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.Bootcamp.alexander.bankapi.model.Deposit;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.Bootcamp.alexander.bankapi.exception.AccountNotFoundException;
import org.Bootcamp.alexander.bankapi.model.Card;
import org.Bootcamp.alexander.bankapi.service.AccountService;
import org.Bootcamp.alexander.bankapi.model.Account;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * @AccountHandler - класс содержащий логику работы
 * запросов таких как POST, GET для класса Account, который в
 * свою очередь реализует интерфейс AccountService.
 */

public class AccountHandler implements HttpHandler, ResponseSender {
    private final AccountService accountService;

    public AccountHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // GET метод
        if (exchange.getRequestMethod().equals("GET")) {
            // Проверка баланса аккаунта
            if (exchange.getRequestURI().getPath().equals("/account/balance")) {
                byte[] response;

                // получаем номер счета по URL
                String[] arr = exchange.getRequestURI().getQuery().split("=");
                if (arr.length < 2) {
                    response = "Incorrect query.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 400, response);
                    return;
                }
                if (!arr[0].equals("number")) {
                    response = "Wrong parameter name.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 400, response);
                    return;
                }

                BigDecimal balance;
                String accountNumber = arr[1];
                try {
                    balance = accountService.getAccountBalance(accountNumber);
                } catch (SQLException ex) {
                    response = ex.getMessage().getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 500, response);
                    return;
                }
                if (balance == null) {
                    sendResponse(exchange, 404, "Account doesn't exist".getBytes(StandardCharsets.UTF_8));
                    return;
                }

                // Если все корректно
                response = balance.toPlainString().getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 200, response);
            }
        }
        // POST метод
        if (exchange.getRequestMethod().equals("POST")) {
            // Пополнить
            if (exchange.getRequestURI().getPath().equals("/account/pay")) {
                byte[] response;

                try {
                    Deposit deposit = new ObjectMapper().readValue(exchange.getRequestBody(), Deposit.class);
                    accountService.topUpAccountBalance(deposit);
                } catch (JsonMappingException ex) {
                    response = "Wrong number of parameters.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 400, response);
                    return;
                } catch (JsonParseException ex) {
                    response = "Incorrect data.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 400, response);
                    return;
                } catch (AccountNotFoundException ex) {
                    response = "Account doesn't exists.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 404, response);
                    return;
                }

                // Если все корректно
                response = "Balance replenished.".getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 200, response);
            } else{
                if(exchange.getRequestURI().getPath().equals("/account/create")){
                    byte[] response;

                    try {
                        Account account = new ObjectMapper().readValue(exchange.getRequestBody(), Account.class);
                        accountService.insertAccountInDataBase(account);
                    } catch (JsonMappingException ex) {
                        response = "Wrong number of parameters.".getBytes(StandardCharsets.UTF_8);
                        sendResponse(exchange, 400, response);
                        return;
                    } catch (JsonParseException ex) {
                        response = "Incorrect data.".getBytes(StandardCharsets.UTF_8);
                        sendResponse(exchange, 400, response);
                        return;
                    } catch (AccountNotFoundException ex) {
                        response = "Account already exists.".getBytes(StandardCharsets.UTF_8);
                        sendResponse(exchange, 404, response);
                        return;
                    } catch (SQLException ex) {
                    response = ex.getMessage().getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 500, response);
                    return;
                }

                    // Если все корректно
                    response = "Account created.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 200, response);
                }
            }
        } if (exchange.getRequestMethod().equals("DELETE")) {

            if (exchange.getRequestURI().getPath().equals("/account/delete")) {
                byte[] response;

                try {
                    Account account = new ObjectMapper().readValue(exchange.getRequestBody(), Account.class);
                    accountService.deleteAccountFromDataBase(account);
                } catch (SQLException ex) {
                    response = ex.getMessage().getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 500, response);
                    return;
                }catch (JsonMappingException ex) {
                    response = "Wrong number of parameters.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 400, response);
                    return;
                } catch (JsonParseException ex) {
                    response = "Incorrect data.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 400, response);
                    return;
                } catch (AccountNotFoundException ex) {
                    response = "Account already deleted.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 404, response);
                    return;
                }

                // Если все корректно
                response = ("Account "  +"successfully deleted!").getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 200, response);
            }
        } else {
            sendResponse(exchange, 400, "Incorrect method".getBytes(StandardCharsets.UTF_8));
        }
    }
}
