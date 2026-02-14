package com.samya.irctc.service;

import com.samya.irctc.model.Booking;
import com.samya.irctc.repository.BookingRepository;

public class BookingService {

    private final BookingRepository bookingRepository = new BookingRepository();

    public Booking bookTicket(int userId, int trainId) {
        return bookingRepository.create(userId, trainId);
    }
}
