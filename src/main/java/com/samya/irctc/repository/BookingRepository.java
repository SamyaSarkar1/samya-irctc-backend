package com.samya.irctc.repository;

import com.samya.irctc.model.Booking;
import com.samya.irctc.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    public Booking create(int userId, int trainId) {
        String sql = "INSERT INTO bookings (user_id, train_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

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

    public List<Booking> findByUserId(int userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("train_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean deleteById(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
