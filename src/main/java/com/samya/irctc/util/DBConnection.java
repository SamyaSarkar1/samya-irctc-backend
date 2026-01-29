package com.samya.irctc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            String databaseUrl = System.getenv("DATABASE_URL");

            if (databaseUrl == null || databaseUrl.isEmpty()) {
                throw new RuntimeException("DATABASE_URL not found");
            }

            // Example Render URL:
            // postgres://user:password@host:5432/dbname
            databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");

            // Render requires SSL
            if (!databaseUrl.contains("?")) {
                databaseUrl += "?sslmode=require";
            } else {
                databaseUrl += "&sslmode=require";
            }

            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(databaseUrl);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed");
        }
    }
}
