package com.samya.irctc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }
}
