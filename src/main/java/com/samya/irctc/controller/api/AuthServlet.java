package com.samya.irctc.controller.api;

import com.google.gson.Gson;
import com.samya.irctc.model.User;
import com.samya.irctc.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/auth/login")
public class AuthServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        User loginUser = gson.fromJson(reader, User.class);

        User user = userService.login(loginUser.getEmail(), loginUser.getPassword());

        resp.setContentType("application/json");

        if (user != null) {
            resp.getWriter().write("{\"success\": true}");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"success\": false, \"message\": \"Invalid credentials\"}");
        }
    }
}
