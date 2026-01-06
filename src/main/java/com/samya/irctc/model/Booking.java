package com.samya.irctc.model;

import java.sql.Timestamp;

public class Booking {

    private int id;
    private int userId;
    private int trainId;
    private Timestamp bookingDate;

    public Booking() {}

    public Booking(int id, int userId, int trainId, Timestamp bookingDate) {
        this.id = id;
        this.userId = userId;
        this.trainId = trainId;
        this.bookingDate = bookingDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getTrainId() {
        return trainId;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }
}




