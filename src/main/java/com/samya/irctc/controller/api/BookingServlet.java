package com.samya.irctc.controller.api;

import com.google.gson.Gson;
import com.samya.irctc.model.Booking;
import com.samya.irctc.service.BookingService;
import com.samya.irctc.util.JwtUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/bookings")
public class BookingServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();
    private final Gson gson = new Gson();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        try {

            int userId = JwtUtil.getUserIdFromRequest(req);


            @SuppressWarnings("unchecked")
            Map<String, Object> body =
                    gson.fromJson(req.getReader(), Map.class);

            if (body == null || !body.containsKey("trainId")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"trainId is required\"}");
                return;
            }

            int trainId = ((Number) body.get("trainId")).intValue();


            Booking booking =
                    bookingService.createBooking(userId, trainId);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            gson.toJson(booking, resp.getWriter());

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid request\"}");
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        try {
            int userId = JwtUtil.getUserIdFromRequest(req);
            gson.toJson(
                    bookingService.getBookingsByUser(userId),
                    resp.getWriter()
            );
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Unauthorized\"}");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        try {
            int userId = JwtUtil.getUserIdFromRequest(req);
            int bookingId = Integer.parseInt(req.getParameter("id"));

            boolean deleted =
                    bookingService.deleteBooking(userId, bookingId);

            if (deleted) {
                resp.getWriter().write("{\"message\":\"Booking cancelled\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().write("{\"error\":\"Not allowed\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid request\"}");
        }
    }
}
