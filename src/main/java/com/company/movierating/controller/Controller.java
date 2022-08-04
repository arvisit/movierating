package com.company.movierating.controller;

import java.io.IOException;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.dao.connection.DataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("/controller")
@SuppressWarnings("serial")
public class Controller extends HttpServlet {
    private final ExceptionHandler exceptionHandler = ExceptionHandler.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Got request");
        String command = req.getParameter("command");
        Command commandInstance = CommandFactory.getInstance().getCommand(command);
        String page;
        try {
            page = commandInstance.execute(req);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            page = exceptionHandler.handleException(req, resp, e);
        }
        req.getRequestDispatcher(page).forward(req, resp);
    }

    @Override
    public void destroy() {
        DataSource.getInstance().close();
    }
}
