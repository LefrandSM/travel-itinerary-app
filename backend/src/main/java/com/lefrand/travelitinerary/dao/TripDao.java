package com.lefrand.travelitinerary.dao;

import com.lefrand.travelitinerary.config.DatabaseConnection;
import com.lefrand.travelitinerary.model.Trip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Trip> readAllTrips() {
        List<Trip> trips = new ArrayList<>();

        String sql = "SELECT * FROM trip";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                Trip trip = new Trip();

                trip.setId(rs.getInt("id"));
                trip.setName(rs.getString("name"));
                trip.setCity(rs.getString("city"));
                trip.setDateStart(rs.getDate("date_start"));
                trip.setDateEnd(rs.getDate("date_end"));
                trip.setUserId(rs.getInt("user_id"));

                trips.add(trip);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return trips;
    }
    public Trip getTripById(Integer id) {
        String sql = "SELECT * FROM trip WHERE id = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            result.next();

            Trip trip = new Trip();

            trip.setId(id);
            trip.setName(result.getString("name"));
            trip.setCity(result.getString("city"));
            trip.setDateStart(result.getDate("date_start"));
            trip.setDateEnd(result.getDate("date_end"));
            trip.setUserId(result.getInt("user_id"));

            return trip;
        } catch (SQLException e) {
            throw new RuntimeException("There is no data");
        }
    }
}
