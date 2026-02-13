package com.samya.irctc.controller.api;

import com.google.gson.Gson;
import com.samya.irctc.model.User;
import com.samya.irctc.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/users")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        User user = gson.fromJson(reader, User.class);

        boolean success = userService.register(user);

        resp.setContentType("application/json");
        resp.getWriter().write("{\"success\": " + success + "}");
    }
}
