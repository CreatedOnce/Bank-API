package org.Bootcamp.alexander.bankapi.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.Bootcamp.alexander.bankapi.exception.AccountNotFoundException;
import org.Bootcamp.alexander.bankapi.model.Account;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.Bootcamp.alexander.bankapi.model.Card;
import org.Bootcamp.alexander.bankapi.service.CardService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * @AccountHandler - класс содержащий логику работы
 * запросов таких как POST, GET для класса Card, который в
 * свою очередь реализует интерфейс CardService.
 */

public class CardHandler implements HttpHandler, ResponseSender{
    private final CardService cardService;

    public CardHandler(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // GET метод
        if (exchange.getRequestMethod().equals("GET")) {
            // View all cards
            if (exchange.getRequestURI().getPath().equals("/card/view")) {
                byte[] response;
                ArrayNode arr;

                // Получаем карточки как массив JSON обьектов
                try {
                    arr = cardService.getCards();
                } catch (SQLException ex) {
                    response = "Can not get cards.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 500, response);
                    return;
                }

                // Если все корректно
                response = arr.toPrettyString().getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 200, response);
            }
        }
        // POST метод
        if (exchange.getRequestMethod().equals("POST")) {
            // Добавляем новую карту
            if (exchange.getRequestURI().getPath().equals("/card/order")) {
                byte[] response;

                try {
                    Card card = new ObjectMapper().readValue(exchange.getRequestBody(), Card.class); // получаем java model from JSON
                    cardService.insertCardInDatabase(card);
                } catch (JsonMappingException ex) {
                    response = "Wrong number of parameters.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 400, response);
                    return;
                } catch (JsonParseException ex) {
                    response = "Incorrect data.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 400, response);
                    return;
                } catch (JdbcSQLIntegrityConstraintViolationException ex) {
                    response = "Card with this number exists.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 404, response);
                    return;
                } catch (SQLException ex) {
                    response = ex.getMessage().getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 500, response);
                    return;
                }

                // Если все корректно
                response = "Card added.".getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 201, response);
            }
        } if (exchange.getRequestMethod().equals("DELETE")) {

            if (exchange.getRequestURI().getPath().equals("/account/delete")) {
                byte[] response;

                try {
                    Card card = new ObjectMapper().readValue(exchange.getRequestBody(), Card.class);
                    cardService.deleteCardFromDataBase(card);
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
                    response = "Card already deleted.".getBytes(StandardCharsets.UTF_8);
                    sendResponse(exchange, 404, response);
                    return;
                }

                // Если все корректно
                response = ("Card "  +"successfully deleted!").getBytes(StandardCharsets.UTF_8);
                sendResponse(exchange, 200, response);
            }
        } else {
            sendResponse(exchange, 400, "Incorrect method".getBytes(StandardCharsets.UTF_8));
        }
    }
}
