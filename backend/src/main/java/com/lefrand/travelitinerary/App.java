package com.lefrand.travelitinerary;

import com.lefrand.travelitinerary.api.TripHandler;
import com.lefrand.travelitinerary.dao.TripDao;
import com.lefrand.travelitinerary.model.Trip;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.sql.Date;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    static void main() throws Exception {

//         Java server HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/trips", new TripHandler());
        server.start();
        System.out.println("Server running on http://localhost:8080");

//        Create
//        Trip trip = new Trip();
//
//        trip.setName("paris trip in winter");
//        trip.setCity("Paris");
//        trip.setDateStart(Date.valueOf(LocalDate.of(2026, 9, 23)));
//        trip.setDateEnd(Date.valueOf(LocalDate.of(2026, 9, 25)));
//        trip.setUserId(2);
//
//        TripDao tripDao = new TripDao();
//        tripDao.createTrip(trip);

//        Read all show in terminal
//        TripDao trips = new TripDao();
//
//        for(Trip t : trips.readAllTrips()) {
//            System.out.println(t.getName());
//        }

//        Read data by id
//        TripDao tripDao = new TripDao();
//        Trip trip = tripDao.getTripById(4);
//
//        System.out.println(trip.getName());

//        Delete data by id
//        TripDao tripDao = new TripDao();
//        tripDao.deleteTrip(3);

//        TripDao tripDao = new TripDao();
//        Trip trip = tripDao.getTripById(6);
//        Trip tripUpdate = new Trip();
//        tripUpdate.setName("Fligt to home");
//        tripUpdate.setCity("Payakumbuh, Bukittingi");
//        tripUpdate.setDateStart(Date.valueOf(LocalDate.of(2027, 2, 25)));
//        tripUpdate.setDateEnd(Date.valueOf(LocalDate.of(2027, 3, 20)));
//
//        tripDao.updateTrip(tripUpdate, tripUpdate.getId());
    }
}
