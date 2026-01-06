package com.samya.irctc.controller.api;

import com.google.gson.Gson;
import com.samya.irctc.model.Booking;
import com.samya.irctc.service.BookingService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/bookings/*")
public class BookingServlet extends HttpServlet {

    private final BookingService service = new BookingService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo(); // /create /user /delete

        // ➕ CREATE BOOKING
        if ("/create".equals(path)) {

            int userId = Integer.parseInt(req.getParameter("userId"));
            int trainId = Integer.parseInt(req.getParameter("trainId"));

            Booking booking = service.createBooking(userId, trainId);

            if (booking == null) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                resp.getWriter().write("{\"error\":\"Booking already exists\"}");
                return;
            }

            resp.getWriter().write(gson.toJson(booking));
        }

        // 📄 GET USER BOOKINGS
        else if ("/user".equals(path)) {

            int userId = Integer.parseInt(req.getParameter("userId"));
            List<Booking> bookings = service.getBookingsByUser(userId);

            resp.getWriter().write(gson.toJson(bookings));
        }

        // ❌ DELETE BOOKING
        else if ("/delete".equals(path)) {

            int bookingId = Integer.parseInt(req.getParameter("bookingId"));
            boolean deleted = service.deleteBooking(bookingId);

            if (deleted) {
                resp.getWriter().write("{\"message\":\"Booking deleted successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Booking not found\"}");
            }
        }

        // ❌ INVALID
        else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Invalid endpoint\"}");
        }
    }
}


