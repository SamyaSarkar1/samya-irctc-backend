package com.samya.irctc.repository;

import com.samya.irctc.model.User;
import com.samya.irctc.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserRepository {

    public boolean register(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
