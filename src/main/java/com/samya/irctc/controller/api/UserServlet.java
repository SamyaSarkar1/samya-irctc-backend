package com.samya.irctc.controller.api;

import com.samya.irctc.model.User;
import com.samya.irctc.service.UserService;
import com.samya.irctc.exception.DuplicateEmailException;
import com.samya.irctc.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/user")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        User inputUser = mapper.readValue(req.getInputStream(), User.class);

        try {
            User createdUser = userService.register(
                    inputUser.getName(),
                    inputUser.getEmail(),
                    inputUser.getPassword()
            );

            resp.setStatus(HttpServletResponse.SC_CREATED);

            Map<String, Object> response = new HashMap<>();
            response.put("id", createdUser.getId());
            response.put("name", createdUser.getName());
            response.put("email", createdUser.getEmail());

            mapper.writeValue(resp.getOutputStream(), response);

        } catch (DuplicateEmailException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getOutputStream(),
                    Map.of("error", "Email already exists"));
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getOutputStream(),
                    Map.of("error", "Email and password required"));
            return;
        }

        User user = userService.login(email, password);

        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(resp.getOutputStream(),
                    Map.of("error", "Invalid email or password"));
            return;
        }


        String token = JwtUtil.generateToken(user.getId(), user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail()
        ));

        mapper.writeValue(resp.getOutputStream(), response);
    }
}











