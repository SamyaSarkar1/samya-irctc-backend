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

    static class BookingRequest {
        int userId;
        int trainId;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        BookingRequest bookingRequest = gson.fromJson(reader, BookingRequest.class);

        if (bookingRequest == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid request\"}");
            return;
        }

        Booking booking = bookingService.bookTicket(
                bookingRequest.userId,
                bookingRequest.trainId
        );

        Map<String, Object> response = new HashMap<>();
        response.put("success", booking != null);
        response.put("booking", booking);

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(response));
    }
}
