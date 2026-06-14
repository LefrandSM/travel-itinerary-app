package com.lefrand.travelitinerary.dao;

import com.lefrand.travelitinerary.config.DatabaseConnection;
import com.lefrand.travelitinerary.model.Trip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TripDao {
    public void createTrip(Trip trip) {
        String sql = "INSERT INTO trip (name, city, date_start, date_end, user_id) VALUES (?,?,?,?,?)";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trip.getName());
            stmt.setString(2, trip.getCity());
            stmt.setDate(3, trip.getDateStart());
            stmt.setDate(4, trip.getDateEnd());
            stmt.setInt(5, trip.getUserId());

            stmt.executeUpdate();


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
