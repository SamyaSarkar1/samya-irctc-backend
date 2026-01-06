package com.samya.irctc.service;

import com.samya.irctc.model.User;
import com.samya.irctc.repository.UserRepository;

public class UserService {

    private final UserRepository repository = new UserRepository();

    public User register(User user) {
        try {
            return repository.save(user);
        } catch (RuntimeException e) {
            // Duplicate email handling
            if (e.getCause() != null &&
                    e.getCause().getMessage().contains("users.email")) {
                throw new RuntimeException("EMAIL_ALREADY_EXISTS");
            }
            throw e;
        }
    }

    public User login(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }
}




