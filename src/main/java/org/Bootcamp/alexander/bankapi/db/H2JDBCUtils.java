package org.Bootcamp.alexander.bankapi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @H@JDBCUtils - класс содежащий в себе метод для
 * создания подключения в с базой данных.
 */

public class H2JDBCUtils {
    private static final String jdbcURL = "jdbc:h2:mem:bankDB;DB_CLOSE_DELAY=-1";
    private static final String jdbcUser = "sa";
    private static final String jdbcPassword = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return connection;
    }
}
