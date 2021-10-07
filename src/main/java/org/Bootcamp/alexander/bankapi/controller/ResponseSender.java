package org.Bootcamp.alexander.bankapi.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public interface ResponseSender {
    /**
     *
     * @param exchange - обьект класса HttpExchange который инкапсулирует в себе полученный запрос и ответ
     * @param rCode - код состояния
     * @param response -наш ответ в виде массива байтов
     */

    default void sendResponse(HttpExchange exchange, int rCode, byte[] response) throws IOException {
        OutputStream os = exchange.getResponseBody();//Считываем OutputStream для отправки
        exchange.sendResponseHeaders(rCode, response.length);
        os.write(response);
        os.close();
    }
}
