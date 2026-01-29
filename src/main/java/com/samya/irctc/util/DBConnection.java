package com.samya.irctc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            String rawUrl = System.getenv("DATABASE_URL");

            if (rawUrl == null || rawUrl.isEmpty()) {
                throw new RuntimeException("DATABASE_URL not set");
            }

            // Convert Render URL → JDBC URL
            rawUrl = rawUrl.replace("postgresql://", "");

            String[] parts = rawUrl.split("@");
            String[] creds = parts[0].split(":");
            String[] hostDb = parts[1].split("/");

            String user = creds[0];
            String password = creds[1];
            String host = hostDb[0];
            String db = hostDb[1];

            String jdbcUrl =
                    "jdbc:postgresql://" + host + "/" + db +
                            "?user=" + user + "&password=" + password;

            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(jdbcUrl);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed");
        }
    }
}
