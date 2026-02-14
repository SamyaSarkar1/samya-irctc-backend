package com.samya.irctc.repository;

import com.samya.irctc.model.Booking;
import com.samya.irctc.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    public Booking create(int userId, int trainId) {
        String updateSeatSql =
                "UPDATE trains SET available_seats = available_seats - 1 WHERE id = ? AND available_seats > 0";
        String insertSql =
                "INSERT INTO bookings (user_id, train_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement seatPs = conn.prepareStatement(updateSeatSql);
            seatPs.setInt(1, trainId);
            int rows = seatPs.executeUpdate();

            if (rows == 0) {
                System.out.println("No seats available!");
                return null;
            }


            PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
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


    public boolean deleteById(int bookingId) {
        String getTrainSql = "SELECT train_id FROM bookings WHERE id = ?";
        String deleteSql = "DELETE FROM bookings WHERE id = ?";
        String increaseSeatSql =
                "UPDATE trains SET available_seats = available_seats + 1 WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {


            PreparedStatement getPs = conn.prepareStatement(getTrainSql);
            getPs.setInt(1, bookingId);
            ResultSet rs = getPs.executeQuery();

            if (!rs.next()) return false;
            int trainId = rs.getInt("train_id");


            PreparedStatement deletePs = conn.prepareStatement(deleteSql);
            deletePs.setInt(1, bookingId);
            deletePs.executeUpdate();


            PreparedStatement incPs = conn.prepareStatement(increaseSeatSql);
            incPs.setInt(1, trainId);
            incPs.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
