package com.samya.irctc.controller.api;

import com.google.gson.Gson;
import com.samya.irctc.model.Booking;
import com.samya.irctc.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/bookings")
public class BookingServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        Map<String, Double> body = gson.fromJson(reader, Map.class);

        int userId = body.get("userId").intValue();
        int trainId = body.get("trainId").intValue();

        Booking booking = bookingService.bookTicket(userId, trainId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", booking != null);
        response.put("booking", booking);

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response));
    }
}
