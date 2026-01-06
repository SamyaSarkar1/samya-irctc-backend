package com.samya.irctc.controller.api;

import com.google.gson.Gson;
import com.samya.irctc.model.Train;
import com.samya.irctc.service.TrainService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/trains")
public class TrainServlet extends HttpServlet {

    private final TrainService trainService = new TrainService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        System.out.println(">>> TrainServlet HIT <<<");

        String source = req.getParameter("source");
        String destination = req.getParameter("destination");

        List<Train> trains = trainService.getTrains(source, destination);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(trains));
    }
}


