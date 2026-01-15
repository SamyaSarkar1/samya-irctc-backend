package com.samya.irctc.repository;

import com.samya.irctc.model.User;
import com.samya.irctc.util.DBConnection;

import java.sql.*;

public class UserRepository {


    public User findByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public User save(String name, String email, String hashedPassword) {

        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new User(
                        rs.getInt(1),
                        name,
                        email,
                        hashedPassword
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}









