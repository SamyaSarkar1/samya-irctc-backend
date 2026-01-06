package com.samya.irctc.service;

import com.samya.irctc.model.Train;
import com.samya.irctc.repository.TrainRepository;

import java.util.List;

public class TrainService {

    private final TrainRepository repository = new TrainRepository();

    public List<Train> getTrains(String source, String destination) {
        return repository.findTrains(source, destination);
    }
}





