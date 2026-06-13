package com.lefrand.travelitinerary.model;

import java.time.LocalDateTime;

public class PlannedItem {
    private int id;
    private String type;
    private String description;
    private String locationText;
    private String locationMapUrl; // nullable
    private Integer cost; // nullable

    private LocalDateTime dateTimeStart; // required!

    private LocalDateTime dateTimeEnd; // nullable

    private int tripId;

    public PlannedItem(int id, String type, String description, String locationText, String locationMapUrl, Integer cost, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, int tripId) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.locationText = locationText;
        this.locationMapUrl = locationMapUrl;
        this.cost = cost;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.tripId = tripId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public String getLocationMapUrl() {
        return locationMapUrl;
    }

    public void setLocationMapUrl(String locationMapUrl) {
        this.locationMapUrl = locationMapUrl;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
}
