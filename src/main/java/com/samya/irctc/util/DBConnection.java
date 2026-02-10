package com.samya.irctc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {

            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            if (url == null || user == null || password == null) {
                throw new RuntimeException("Database environment variables are not set");
            }

            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("PostgreSQL Driver not found", e);
            }

            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
