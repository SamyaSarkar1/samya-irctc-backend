package com.samya.irctc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        try {
            String databaseUrl = System.getenv("DATABASE_URL");

            if (databaseUrl == null) {
                throw new RuntimeException("DATABASE_URL not set");
            }

            // Convert Render URL → JDBC URL
            // postgres://user:pass@host:5432/db
            databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");

            return DriverManager.getConnection(databaseUrl);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed");
        }
    }
}
