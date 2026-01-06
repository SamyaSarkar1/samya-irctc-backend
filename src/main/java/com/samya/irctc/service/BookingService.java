package com.samya.irctc.service;

import com.samya.irctc.model.Booking;
import com.samya.irctc.repository.BookingRepository;

import java.util.List;

public class BookingService {

    private final BookingRepository repository = new BookingRepository();

    // ➕ CREATE WITH DUPLICATE CHECK
    public Booking createBooking(int userId, int trainId) {

        if (repository.exists(userId, trainId)) {
            return null; // duplicate
        }

        return repository.save(userId, trainId);
    }

    // 📄 USER BOOKINGS
    public List<Booking> getBookingsByUser(int userId) {
        return repository.findByUser(userId);
    }

    // ❌ DELETE
    public boolean deleteBooking(int bookingId) {
        return repository.delete(bookingId);
    }
}



