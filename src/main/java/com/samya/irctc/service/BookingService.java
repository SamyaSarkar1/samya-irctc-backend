package com.samya.irctc.service;

import com.samya.irctc.model.Booking;
import com.samya.irctc.repository.BookingRepository;

import java.util.List;

public class BookingService {

    private final BookingRepository bookingRepository = new BookingRepository();

    public Booking createBooking(int userId, int trainId) {
        return bookingRepository.create(userId, trainId);
    }

    public List<Booking> getBookingsByUserId(int userId) {
        return bookingRepository.findByUserId(userId);
    }
}
