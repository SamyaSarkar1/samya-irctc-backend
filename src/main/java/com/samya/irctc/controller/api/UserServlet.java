package com.samya.irctc.controller.api;

import com.google.gson.Gson;
import com.samya.irctc.model.User;
import com.samya.irctc.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/users/*")
public class UserServlet extends HttpServlet {

    private final UserService service = new UserService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        String path = req.getPathInfo(); // /register or /login

        // ================= REGISTER =================
        if ("/register".equals(path)) {

            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if (name == null || email == null || password == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Missing parameters\"}");
                return;
            }

            try {
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);

                User savedUser = service.register(user);

                // 🔐 hide password in response
                savedUser.setPassword(null);

                resp.getWriter().write(gson.toJson(savedUser));

            } catch (RuntimeException e) {

                if ("EMAIL_ALREADY_EXISTS".equals(e.getMessage())) {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409
                    resp.getWriter().write("{\"error\":\"Email already registered\"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().write("{\"error\":\"User registration failed\"}");
                }
            }
        }

        // ================= LOGIN =================
        else if ("/login".equals(path)) {

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if (email == null || password == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Missing parameters\"}");
                return;
            }

            User user = service.login(email, password);

            if (user == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"error\":\"Invalid email or password\"}");
                return;
            }

            // 🔐 hide password
            user.setPassword(null);

            resp.getWriter().write(gson.toJson(user));
        }

        // ================= INVALID =================
        else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Invalid endpoint\"}");
        }
    }
}







