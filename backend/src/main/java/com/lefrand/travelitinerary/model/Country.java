package com.lefrand.travelitinerary.model;

import java.util.List;

public class Country {
    private int id;
    private String name;

    private List<Trip> trips; // a country could have many trips

    public Country(int id, String name, List<Trip> trips) {
        this.id = id;
        this.name = name;
        this.trips = trips;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Trip> getTrips() {
        return trips;
    }
}
