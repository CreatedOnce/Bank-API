package org.Bootcamp.alexander.bankapi;

import com.sun.net.httpserver.HttpServer;
import org.Bootcamp.alexander.bankapi.controller.*;
import org.Bootcamp.alexander.bankapi.db.DAO.AccountDAOImpl;
import org.Bootcamp.alexander.bankapi.db.DAO.CardDAOImpl;
import org.Bootcamp.alexander.bankapi.db.DBInitializer;
import org.Bootcamp.alexander.bankapi.service.*;
import org.Bootcamp.alexander.bankapi.service.CardServiceImpl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

public class MainApp {

    public static void main(String[] args)throws SQLException, IOException {

       DBInitializer.init();
       ServerStarter.start();
    }
    public static class ServerStarter {
        public static void start() throws IOException {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            AccountService accountService = new AccountServiceImpl(new AccountDAOImpl());
            CardService cardService = new CardServiceImpl(new CardDAOImpl());


            server.createContext("/card/order", new CardHandler(cardService));
            server.createContext("/card/view", new CardHandler(cardService));
            server.createContext("/account/create", new AccountHandler(accountService));
            server.createContext("/account/delete", new AccountHandler(accountService));
            server.createContext("/account/pay", new AccountHandler(accountService));
            server.createContext("/account/balance", new AccountHandler(accountService));

            server.start();
        }
    }
}
