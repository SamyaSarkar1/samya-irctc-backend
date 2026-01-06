package com.samya.irctc.model;

public class Train {

    private int trainNo;
    private String trainName;
    private String source;
    private String destination;

    // REQUIRED no-arg constructor (VERY IMPORTANT)
    public Train() {
    }

    // Optional constructor
    public Train(int trainNo, String trainName, String source, String destination) {
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(int trainNo) {
        this.trainNo = trainNo;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}




