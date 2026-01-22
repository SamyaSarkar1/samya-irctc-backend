package com.samya.irctc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            String dbUrl = System.getenv("DATABASE_URL");

            if (dbUrl == null || dbUrl.isEmpty()) {
                throw new RuntimeException("DATABASE_URL not found in environment variables");
            }

            return DriverManager.getConnection(dbUrl);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed");
        }
    }
}
