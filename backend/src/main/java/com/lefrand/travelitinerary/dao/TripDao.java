package com.lefrand.travelitinerary.dao;

import com.lefrand.travelitinerary.config.DatabaseConnection;
import com.lefrand.travelitinerary.model.Country;
import com.lefrand.travelitinerary.model.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripDao {
    public boolean createTrip(Trip trip) {
        String insertTripSql = "INSERT INTO trip (name, city, date_start, date_end, user_id) VALUES (?,?,?,?,?)";

        String insertCountrySql = """
                INSERT INTO trip_country (trip_id, country_id) VALUES (?, ?)
                """;

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmtTrip = conn.prepareStatement(insertTripSql, Statement.RETURN_GENERATED_KEYS // to take the new id that was generated in trip table and insert it into trip_country table
            )) {

            // don't save changes to the database immediately
            conn.setAutoCommit(false);

            stmtTrip.setString(1, trip.getName());
            stmtTrip.setString(2, trip.getCity());
            stmtTrip.setDate(3, java.sql.Date.valueOf(trip.getDateStart()));
            stmtTrip.setDate(4, java.sql.Date.valueOf(trip.getDateEnd()));
            stmtTrip.setInt(5, trip.getUserId());

            // execute Trip Insert Query
            int rowsAffectedTrip = stmtTrip.executeUpdate();

            // take id that was generated from trip table
            ResultSet resultSet = stmtTrip.getGeneratedKeys();

            int tripId = 0;
            if(resultSet.next()) {
                tripId = resultSet.getInt(1);
            }

            // insert countries (join table trip_country)
            PreparedStatement stmtCountry = conn.prepareStatement(insertCountrySql);

            if(trip.getCountryIds() != null) {
                for (Integer countryId : trip.getCountryIds()) {
                    stmtCountry.setInt(1, tripId);
                    stmtCountry.setInt(2, countryId);
                    // store this SQL in a batch queue
                    stmtCountry.addBatch();
                }

                // execute batch (all query)
                int[] rowsAffectedTripCountry = stmtCountry.executeBatch();
            }

            // save everything in DATABASE
            conn.commit();

            return rowsAffectedTrip > 0;

        }catch (SQLException e) {
            throw new RuntimeException("Failed to create Trip", e);
        }
    }
    public List<Trip> readAllTrips() {
        String sql = """
                SELECT 
                    t.id AS trip_id,
                    t.name,
                    t.city,
                    t.date_start,
                    t.date_end,
                    t.user_id,
                    c.id AS country_id,
                    c.name as country_name
                FROM trip t
                LEFT JOIN trip_country tc ON t.id = tc.trip_id
                LEFT JOIN country c ON c.id = tc.country_id                
                """;
        Map<Integer, Trip> tripMap = new HashMap<>();

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery()) {
            while(resultSet.next()) {
                int tripId = resultSet.getInt("trip_id");
                Trip trip = tripMap.get(tripId);

                // create trip only once
                // check if Key has value (no object == null)
                if(trip == null) {
                    trip = new Trip();
                    trip.setId(resultSet.getInt("trip_id"));
                    trip.setName(resultSet.getString("name"));
                    trip.setCity(resultSet.getString("city"));
                    trip.setDateStart(resultSet.getDate("date_start").toLocalDate());
                    trip.setDateEnd(resultSet.getDate("date_end").toLocalDate());
                    trip.setUserId(resultSet.getInt("user_id"));
                    trip.setCountries(new ArrayList<>());

                    tripMap.put(tripId, trip);
                }

                int countryId = resultSet.getInt("country_id");

                // add country (maybe null)
                if(countryId != 0) {
                    Country country = new Country();
                    country.setId(countryId);
                    country.setName(resultSet.getString("country_name"));

                    // return List<Country> and .add(new Country)
                    trip.getCountries().add(country);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(tripMap.values());
    }
    public List<Trip> getTripById(Integer id) {
        List<Trip> trips = new ArrayList<>();
        String sql = """
            SELECT
                t.id AS trip_id,
                t.name,
                t.city,
                t.date_start,
                t.date_end,
                t.user_id,
                c.id AS country_id,
                c.name as country_name
            FROM trip t
            LEFT JOIN trip_country tc ON t.id = tc.trip_id
            LEFT JOIN country c ON c.id = tc.country_id
            WHERE t.id = ?
        """;
        Map<Integer, Trip> tripMap = new HashMap<>();
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                int tripId = resultSet.getInt("trip_id");
                Trip trip = tripMap.get(tripId);
                if(trip == null) {
                    trip = new Trip();

                    trip.setId(resultSet.getInt("trip_id"));
                    trip.setName(resultSet.getString("name"));
                    trip.setCity(resultSet.getString("city"));
                    trip.setDateStart(resultSet.getDate("date_start").toLocalDate());
                    trip.setDateEnd(resultSet.getDate("date_end").toLocalDate());
                    trip.setUserId(resultSet.getInt("user_id"));
                    trip.setCountries(new ArrayList<>());

                    tripMap.put(tripId, trip);
                }
                int countryId = resultSet.getInt("country_id");
                if(countryId != 0) {
                    Country country = new Country();
                    country.setId(resultSet.getInt("country_id"));
                    country.setName(resultSet.getString("country_name"));

                    trip.getCountries().add(country);
                }
            }
            return new ArrayList<>(tripMap.values());
        } catch (SQLException e) {
            throw new RuntimeException("There is no data");
        }
    }
    public boolean deleteTrip(Integer id) {
        String sql = "DELETE FROM trip WHERE id = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean updateTrip(Trip trip, Integer id) {
        String sql = "UPDATE trip SET name = ?, city = ?, date_start = ?, date_end = ? WHERE id = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trip.getName());
            stmt.setString(2, trip.getCity());
            stmt.setDate(3, java.sql.Date.valueOf(trip.getDateStart()));
            stmt.setDate(4, java.sql.Date.valueOf(trip.getDateEnd()));
            stmt.setInt(5, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Trip mapTrip(ResultSet resultSet) throws SQLException {

        Trip trip = new Trip();

        trip.setId(resultSet.getInt("id"));
        trip.setName(resultSet.getString("name"));
        trip.setCity(resultSet.getString("city"));
        trip.setDateStart(resultSet.getDate("date_start").toLocalDate());
        trip.setDateEnd(resultSet.getDate("date_end").toLocalDate());
        trip.setUserId(resultSet.getInt("user_id"));

        return trip;
    }
}
