package com.samya.irctc.service;

import com.samya.irctc.model.User;
import com.samya.irctc.repository.UserRepository;
import com.samya.irctc.util.PasswordUtil;
import com.samya.irctc.exception.DuplicateEmailException;

public class UserService {

    private final UserRepository userRepository = new UserRepository();


    public User register(String name, String email, String password) {


        User existing = userRepository.findByEmail(email);
        if (existing != null) {
            throw new DuplicateEmailException("Email already exists");
        }


        String hashedPassword = PasswordUtil.hashPassword(password);


        return userRepository.save(name, email, hashedPassword);
    }


    public User login(String email, String password) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }

        boolean match = PasswordUtil.verifyPassword(
                password,
                user.getPassword()
        );

        return match ? user : null;
    }
}










