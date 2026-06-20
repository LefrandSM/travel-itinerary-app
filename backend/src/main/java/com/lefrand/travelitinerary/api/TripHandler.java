package com.lefrand.travelitinerary.api;

import com.google.gson.Gson;
import com.lefrand.travelitinerary.dao.TripDao;
import com.lefrand.travelitinerary.model.Trip;
import com.lefrand.travelitinerary.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.util.List;

public class TripHandler implements HttpHandler {
    private final TripDao tripDao = new TripDao();

    @Override
    public void handle(HttpExchange exchange) {
        try {
            // to check request
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            // GET all trips
            if(method.equals("GET") && path.equals("/trips")) {

                // 1. Read from DB
                List<Trip> trips = tripDao.readAllTrips();

                // 2. Convert to JSON
                String json = JsonUtil.toJson(trips);

                // 3. Send response
                // tell client that I'm gonna send JSON data
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                // to tell HTTP status and the size of data that will be sent
                exchange.sendResponseHeaders(200, json.getBytes().length);
                // channel to write data back to client
                OutputStream os = exchange.getResponseBody();
                // convert JSON into bytes and be sent to client
                os.write(json.getBytes());
                // close response
                os.close();
            }

            // GET trip by id
            if(method.equals("GET") && path.startsWith("/trips/")) {
                String[] parts = path.split("/");
                int id = Integer.parseInt(parts[2]);
                List<Trip> trip = tripDao.getTripById(id);
                if (!trip.isEmpty()) {
                    String json = JsonUtil.toJson(trip);
                    exchange.getResponseHeaders().add("Content-type", "application/json");
                    exchange.sendResponseHeaders(200, json.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                } else {
                    String json = "{\"error\":\"Trip not found\"}";
                    exchange.getResponseHeaders().add("Content-type", "application/json");
                    exchange.sendResponseHeaders(404, json.getBytes().length);

                    OutputStream os = exchange.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                }

            }

            // DELETE trip
            if(method.equals("DELETE") && path.startsWith("/trips/")) {
                String[] parts = path.split("/");
                int id = Integer.parseInt(parts[2]);
                boolean deleted = tripDao.deleteTrip(id);
                if(deleted) {
                    exchange.sendResponseHeaders(204, -1);
                } else {
                    String json = "{\"error\":\"Trip not found\"}";
                    exchange.getResponseHeaders().add("Content-type", "application/json");
                    exchange.sendResponseHeaders(404, json.getBytes().length);

                    OutputStream os = exchange.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                }
            }

            // UPDATE trip
            if(method.equals("PATCH") && path.startsWith("/trips/")) {
                String[] parts = path.split("/");
                int id = Integer.parseInt(parts[2]);

                String jsonUpdate = new String(exchange.getRequestBody().readAllBytes());

                Trip trip = JsonUtil.fromJson(jsonUpdate, Trip.class);

                boolean statusUpdate = tripDao.updateTrip(trip ,id);

                if(statusUpdate) {
                    exchange.sendResponseHeaders(204, -1);
                } else {
                    String json = "{\"error\":\"Trip not found\"}";
                    exchange.getResponseHeaders().add("Content-type", "application/json");
                    exchange.sendResponseHeaders(404, json.getBytes().length);

                    OutputStream os = exchange.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
