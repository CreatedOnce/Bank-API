package org.Bootcamp.alexander.bankapi.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private static final String createScriptPath = "src/main/resources/scripts/create_db.sql";
    private static final String fillDbScriptPath = "src/main/resources/scripts/fill_db.sql";
    private static final String selectQuery = "select * from Account";

    public static void init() throws IOException {
        String createQuery = readFile(createScriptPath);
        String fillQuery= readFile(fillDbScriptPath);
        System.out.println(createQuery);
        System.out.println(fillQuery);

        try (Connection connection = H2JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createQuery);
            statement.executeUpdate(fillQuery);

            ResultSet rs = statement.executeQuery(selectQuery);

            while(rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                System.out.println(id);
                System.out.println(name);
            }

            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static String readFile(String path){
        String line = null;
        try(Scanner scanner = new Scanner( new File(path), "UTF-8" ))
        {
            line = scanner.useDelimiter("\\A").next();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return line;
    }
}
