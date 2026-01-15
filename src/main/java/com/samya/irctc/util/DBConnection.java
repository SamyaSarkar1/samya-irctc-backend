package com.samya.irctc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            System.getenv("DB_URL");
    private static final String USER =
            System.getenv("DB_USER");
    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }

    public static Connection getConnection() {
        try {
            if (URL == null || USER == null || PASSWORD == null) {
                throw new RuntimeException(
                        "Database environment variables not set"
                );
            }

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }
}





