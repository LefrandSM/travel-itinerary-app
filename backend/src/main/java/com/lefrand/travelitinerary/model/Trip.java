package com.lefrand.travelitinerary.model;

import java.time.LocalDateTime;
import java.util.List;

public class Trip {
    private int id;
    private String name;
    private String city;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

    private int userId; // trip has 1 user
    private List<Country> countries; // trip could have many countries

    public Trip(int id, String name, String city, LocalDateTime dateStart, LocalDateTime dateEnd, int userId, List<Country> countries) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.userId = userId;
        this.countries = countries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
