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

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * @AccountHandler - класс содержащий логику работы
 * запросов таких как POST, GET для класса Account.
 *
 */


public class AccountHandler implements HttpHandler, ResponseSender {
    private final AccountService accountService;

    public AccountHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // GET method
        if (exchange.getRequestMethod().equals("GET")) {
            // Check account balance
            if (exchange.getRequestURI().getPath().equals("/account/balance")) {
                byte[] response;

                // Get an account number from URL
                // TODO split by & before this
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

                // If all works correctly
                response = balance.toPlainString().getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 200, response);
            }
        }
        // POST method
        if (exchange.getRequestMethod().equals("POST")) {
            // Top up account
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

                // If all works correctly
                response = "Balance replenished.".getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 200, response);
            }
        } else {
            sendResponse(exchange, 400, "Incorrect method".getBytes(StandardCharsets.UTF_8));
        }
    }
}
