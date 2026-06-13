package com.lefrand.travelitinerary.model;

public class TripCountry {
    private int tripId;
    private int countryId;

    public TripCountry(int tripId, int countryId) {
        this.tripId = tripId;
        this.countryId = countryId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
