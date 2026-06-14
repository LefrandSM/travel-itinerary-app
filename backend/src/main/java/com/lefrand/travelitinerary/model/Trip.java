package com.lefrand.travelitinerary.model;

import java.sql.Date;
import java.util.List;

public class Trip {
    private int id;
    private String name;
    private String city;
    private Date dateStart;
    private Date dateEnd;

    private int userId; // trip has 1 user
    private List<Country> countries; // trip could have many countries

    public Trip() {

    }

    public Trip(int id, String name, String city, Date dateStart, Date dateEnd) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
//        this.userId = userId;
//        this.countries = countries;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }

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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
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
