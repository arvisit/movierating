package com.company.movierating.controller;

import java.io.IOException;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.dao.connection.DataSource;
import com.company.movierating.exception.controller.NonAuthorizedException;
import com.company.movierating.exception.controller.RegisterPasswordConfirmationException;
import com.company.movierating.exception.controller.UnsupportedCommandException;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.exception.service.RegistrationValidationException;

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
            page = handleException(req, resp, e);
        }
        req.getRequestDispatcher(page).forward(req, resp);
    }

    private String handleException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        String page;
        if (e instanceof UnsupportedCommandException) {
            if (req.getAttribute("errorMessage") == null) {
                req.setAttribute("errorMessage", e.getMessage());
            }
            resp.setStatus(400);
            page = "jsp/error/400.jsp";
        } else if (e instanceof RegisterPasswordConfirmationException) {
            req.setAttribute("errorMessage", e.getMessage());
            page = "jsp/create/create_user_form.jsp";
        } else if (e instanceof RegistrationValidationException) {
            req.setAttribute("errorMessage", e.getMessage());
            page = "jsp/create/create_user_form.jsp";
        } else if (e instanceof NonAuthorizedException) {
            req.setAttribute("errorMessage", e.getMessage());
            resp.setStatus(401);
            page = "jsp/sign_in/sign_in_form.jsp";
        } else if (e instanceof ForbiddenPageException) {
            req.setAttribute("errorMessage", e.getMessage());
            resp.setStatus(403);
            page = "index.jsp";
        } else if (e instanceof NoRecordFoundException) {
            req.setAttribute("errorMessage", "Page not found");
            resp.setStatus(404);
            page = "jsp/error/404.jsp";
        } else {
            req.setAttribute("errorMessage", "Internal server error");
            resp.setStatus(500);
            page = "jsp/error/500.jsp";
        }
        return page;
    }

    @Override
    public void destroy() {
        DataSource.getInstance().close();
    }
}
