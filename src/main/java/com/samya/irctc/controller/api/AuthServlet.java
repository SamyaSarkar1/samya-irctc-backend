package com.samya.irctc.controller.api;

import com.google.gson.Gson;
import com.samya.irctc.model.User;
import com.samya.irctc.service.UserService;
import com.samya.irctc.util.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> result = new HashMap<>();

        if (user != null) {

            String token = JwtUtil.generateToken(user.getEmail());

            result.put("success", true);
            result.put("token", token);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            result.put("success", false);
            result.put("message", "Invalid email or password");
        }

        resp.getWriter().write(gson.toJson(result));
    }
}
