package org.Bootcamp.alexander.bankapi.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public interface ResponseSender {
    default void sendResponse(HttpExchange exchange, int rCode, byte[] response) throws IOException {
        OutputStream os = exchange.getResponseBody();
        exchange.sendResponseHeaders(rCode, response.length);
        os.write(response);
        os.close();
    }
}
