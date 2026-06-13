package com.lefrand.travelitinerary.model;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;

    private List<Trip> trips; // user has many trips

    public User(int id, String username, String password, List<Trip> trips) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.trips = trips;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
