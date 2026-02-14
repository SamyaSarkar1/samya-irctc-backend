package com.samya.irctc.repository;

import com.samya.irctc.model.Booking;
import com.samya.irctc.util.DBConnection;

import java.sql.*;

public class BookingRepository {

    public Booking create(int userId, int trainId) {
        String sql = "INSERT INTO bookings (user_id, train_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setInt(2, trainId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new Booking(rs.getInt(1), userId, trainId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
