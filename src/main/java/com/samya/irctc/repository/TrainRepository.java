package com.samya.irctc.repository;

import com.samya.irctc.model.Train;
import com.samya.irctc.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainRepository {

    public List<Train> findTrains(String source, String destination) {
        List<Train> trains = new ArrayList<>();

        String sql = """
            SELECT * FROM public.trains
            WHERE source = ? AND destination = ?
        """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, source);
            ps.setString(2, destination);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Train t = new Train();
                t.setTrainNo(rs.getInt("train_no"));
                t.setTrainName(rs.getString("train_name"));
                t.setSource(rs.getString("source"));
                t.setDestination(rs.getString("destination"));
                t.setDepartureTime(rs.getString("departure_time"));
                t.setArrivalTime(rs.getString("arrival_time"));
                t.setTotalSeats(rs.getInt("total_seats"));
                t.setAvailableSeats(rs.getInt("available_seats"));
                trains.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch trains from database");
        }

        return trains;
    }
}
