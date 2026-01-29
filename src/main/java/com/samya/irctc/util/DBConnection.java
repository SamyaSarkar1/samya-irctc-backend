package com.samya.irctc.util;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            String databaseUrl = System.getenv("DATABASE_URL");

            if (databaseUrl == null || databaseUrl.isEmpty()) {
                throw new RuntimeException("DATABASE_URL not set in environment");
            }

            URI dbUri = new URI(databaseUrl);

            String userInfo = dbUri.getUserInfo(); // user:password
            String username = userInfo.split(":")[0];
            String password = userInfo.split(":")[1];

            String jdbcUrl = "jdbc:postgresql://" +
                    dbUri.getHost() + ":" +
                    dbUri.getPort() +
                    dbUri.getPath();

            return DriverManager.getConnection(jdbcUrl, username, password);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed");
        }
    }
}
