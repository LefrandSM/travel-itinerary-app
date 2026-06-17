package com.lefrand.travelitinerary.dao;

import com.lefrand.travelitinerary.config.DatabaseConnection;
import com.lefrand.travelitinerary.model.Trip;

import javax.xml.crypto.Data;
import java.sql.*;
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
            System.out.println("Data is added");

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Trip> readAllTrips() {
        List<Trip> trips = new ArrayList<>();

        String sql = "SELECT * FROM trip";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()) {
            while(resultSet.next()) {
                trips.add(mapTrip(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return trips;
    }
    public List<Trip> getTripById(Integer id) {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM trip WHERE id = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                trips.add(mapTrip(resultSet));
            }

            return trips;
        } catch (SQLException e) {
            throw new RuntimeException("There is no data");
        }
    }
    public void deleteTrip(Integer id) {
        String sql = "DELETE FROM trip WHERE id = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Trip with id = " + id + " succeed deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateTrip(Trip trip, Integer id) {
        String sql = "UPDATE trip SET name = ?, city = ?, date_start = ?, date_end = ? WHERE id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trip.getName());
            stmt.setString(2, trip.getCity());
            stmt.setDate(3, trip.getDateStart());
            stmt.setDate(4, trip.getDateEnd());
            stmt.setInt(5, id);
            stmt.executeUpdate();

            System.out.println("Update succeed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Trip mapTrip(ResultSet resultSet) throws SQLException {

        Trip trip = new Trip();

        trip.setId(resultSet.getInt("id"));
        trip.setName(resultSet.getString("name"));
        trip.setCity(resultSet.getString("city"));
        trip.setDateStart(resultSet.getDate("date_start"));
        trip.setDateEnd(resultSet.getDate("date_end"));
        trip.setUserId(resultSet.getInt("user_id"));

        return trip;
    }
}
