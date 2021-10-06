package org.Bootcamp.alexander.bankapi.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private static final String createScriptPath = "src/main/resources/scripts/create_db.sql";
    private static final String fillDbScriptPath = "src/main/resources/scripts/fill_db.sql";

    public static void init() throws IOException {
        String createQuery = readFile(createScriptPath);
        String fillQuery= readFile(fillDbScriptPath);
        System.out.println(createQuery);
        System.out.println(fillQuery);

    }

    private static String readFile(String path){
        String line = null;
        try
        {
            Scanner input = new Scanner(System.in);
            File file = new File(path);
            input = new Scanner(file);
            while (input.hasNextLine()) {
                line = input.nextLine();
            }
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return line;
    }
}
