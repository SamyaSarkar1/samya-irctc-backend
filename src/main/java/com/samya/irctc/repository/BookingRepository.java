package com.samya.irctc.repository;

import com.samya.irctc.model.Booking;
import com.samya.irctc.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    // 🔁 CHECK DUPLICATE BOOKING
    public boolean exists(int userId, int trainId) {

        String sql = "SELECT id FROM bookings WHERE user_id=? AND train_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, trainId);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ➕ CREATE BOOKING
    public Booking save(int userId, int trainId) {

        String sql = "INSERT INTO bookings (user_id, train_id) VALUES (?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setInt(2, trainId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new Booking(
                        rs.getInt(1),
                        userId,
                        trainId,
                        new Timestamp(System.currentTimeMillis())
                );
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 📄 GET BOOKINGS BY USER
    public List<Booking> findByUser(int userId) {

        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("train_id"),
                        rs.getTimestamp("booking_date")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // ❌ DELETE BOOKING
    public boolean delete(int bookingId) {

        String sql = "DELETE FROM bookings WHERE id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


