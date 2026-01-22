package com.samya.irctc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:postgresql://<HOST>:5432/<DB_NAME>?currentSchema=samya_irctc";

    private static final String USER = "<DB_USER>";
    private static final String PASSWORD = "<DB_PASSWORD>";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed");
        }
    }
}
