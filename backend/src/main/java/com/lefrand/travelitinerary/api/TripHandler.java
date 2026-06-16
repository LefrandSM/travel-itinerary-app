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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
