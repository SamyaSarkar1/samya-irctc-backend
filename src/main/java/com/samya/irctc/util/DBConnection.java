package com.samya.irctc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                String databaseUrl = System.getenv("DATABASE_URL");

                if (databaseUrl == null || databaseUrl.isEmpty()) {
                    throw new RuntimeException("DATABASE_URL environment variable not set");
                }

                connection = DriverManager.getConnection(databaseUrl);
            }
            return connection;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed");
        }
    }
}
