package com.samya.irctc.model;

public class Booking {

    private int id;
    private int userId;
    private int trainId;

    public Booking(int id, int userId, int trainId) {
        this.id = id;
        this.userId = userId;
        this.trainId = trainId;
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
}






