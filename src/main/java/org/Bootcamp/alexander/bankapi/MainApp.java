package org.Bootcamp.alexander.bankapi;

import org.Bootcamp.alexander.bankapi.db.DBInitializer;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args)throws IOException {
        DBInitializer.init();
    }
}
