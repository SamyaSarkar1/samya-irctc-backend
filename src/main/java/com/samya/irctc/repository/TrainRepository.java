package com.samya.irctc.repository;

import com.samya.irctc.model.Train;
import com.samya.irctc.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainRepository {

    public List<Train> findTrains(String source, String destination) {

        List<Train> trains = new ArrayList<>();

        //  Case-insensitive & trimmed matching (VERY IMPORTANT)
        String sql = """
                SELECT train_no, train_name, source, destination
                FROM trains
                WHERE LOWER(TRIM(source)) = LOWER(TRIM(?))
                  AND LOWER(TRIM(destination)) = LOWER(TRIM(?))
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, source);
            ps.setString(2, destination);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // ✅ Works with no-arg constructor
                Train train = new Train();
                train.setTrainNo(rs.getInt("train_no"));
                train.setTrainName(rs.getString("train_name"));
                train.setSource(rs.getString("source"));
                train.setDestination(rs.getString("destination"));

                trains.add(train);
            }

        } catch (Exception e) {
            //  Proper error visibility
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch trains from database", e);
        }

        return trains;
    }
}






