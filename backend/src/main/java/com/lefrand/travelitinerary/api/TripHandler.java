package com.lefrand.travelitinerary.api;

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

            // request getAllTrips
            if(method.equals("GET") && path.equals("/trips")) {

                // 1. Read from DB
                List<Trip> trips = tripDao.readAllTrips();

                // 2. Convert to JSON
                String json = JsonUtil.toJson(trips);

                // 3. Send response
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, json.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(json.getBytes());
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
