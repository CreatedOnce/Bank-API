package org.Bootcamp.alexander.bankapi;

import jdk.jfr.internal.tool.Main;
import org.Bootcamp.alexander.bankapi.db.DBInitializer;
import org.h2.tools.Server;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp {
//    public static Server h2Server ()throws SQLException {
//
//            return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort","9091");
//    }
    public static void main(String[] args)throws SQLException, IOException {

//        Server server = MainApp.h2Server();
//        server.start();
        DBInitializer.init();
        while (true) {

        }

    }
}
