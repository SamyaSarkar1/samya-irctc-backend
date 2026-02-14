package com.samya.irctc.controller.api;

import com.google.gson.Gson;
import com.samya.irctc.model.Booking;
import com.samya.irctc.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/bookings")
public class BookingServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();
    private final Gson gson = new Gson();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");

        BufferedReader reader = req.getReader();
        Booking bookingReq = gson.fromJson(reader, Booking.class);

        Booking booking = bookingService.createBooking(
                bookingReq.getUserId(),
                bookingReq.getTrainId()
        );

        if (booking != null) {
            resp.getWriter().write("{\"success\": true, \"booking\": " + gson.toJson(booking) + "}");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Booking failed\"}");
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");

        String userIdParam = req.getParameter("userId");

        if (userIdParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"userId required\"}");
            return;
        }

        int userId = Integer.parseInt(userIdParam);
        List<Booking> bookings = bookingService.getBookingsByUser(userId);

        resp.getWriter().write(gson.toJson(bookings));
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");

        String idParam = req.getParameter("id");

        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Booking id required\"}");
            return;
        }

        int id = Integer.parseInt(idParam);
        boolean deleted = bookingService.cancelBooking(id);

        if (deleted) {
            resp.getWriter().write("{\"success\": true, \"message\":\"Booking cancelled\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Booking not found\"}");
        }
    }
}
