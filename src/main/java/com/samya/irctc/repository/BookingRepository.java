package com.samya.irctc.repository;

import com.samya.irctc.model.Booking;
import com.samya.irctc.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                return new Booking(
                        rs.getInt(1),
                        userId,
                        trainId
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Booking findById(int bookingId) {

        String sql = "SELECT * FROM bookings WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("train_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Booking> findByUserId(int userId) {

        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("train_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }


    public boolean deleteById(int bookingId) {

        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
