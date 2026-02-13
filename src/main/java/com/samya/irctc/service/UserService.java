package com.samya.irctc.service;

import com.samya.irctc.model.User;
import com.samya.irctc.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public boolean register(User user) {
        return userRepository.register(user);
    }
}
