package com.samya.irctc.repository;

import com.samya.irctc.model.Train;
import com.samya.irctc.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainRepository {

    public List<Train> findTrains(String source, String destination) {

        List<Train> trains = new ArrayList<>();

        String sql = """
            SELECT id, train_no, train_name, source, destination,
                   departure_time, arrival_time, total_seats, available_seats
            FROM public.trains
            WHERE source = ? AND destination = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, source.toUpperCase());
            ps.setString(2, destination.toUpperCase());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Train train = new Train();
                train.setId(rs.getInt("id"));
                train.setTrainNo(rs.getInt("train_no"));
                train.setTrainName(rs.getString("train_name"));
                train.setSource(rs.getString("source"));
                train.setDestination(rs.getString("destination"));
                train.setDepartureTime(rs.getString("departure_time"));
                train.setArrivalTime(rs.getString("arrival_time"));
                train.setTotalSeats(rs.getInt("total_seats"));
                train.setAvailableSeats(rs.getInt("available_seats"));

                trains.add(train);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch trains from database", e);
        }

        return trains;
    }
}
