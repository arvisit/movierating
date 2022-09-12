package com.company.movierating.controller;

import java.io.IOException;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.dao.connection.DataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("/controller")
@MultipartConfig(maxFileSize = Controller.MB * 2, maxRequestSize = Controller.MB * 10)
public class Controller extends HttpServlet {
    private final ExceptionHandler exceptionHandler = ExceptionHandler.INSTANCE;

    public static final int MB = 1024 * 1024;
    private static final String REDIRECT = "redirect:";

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
        Command commandInstance = CommandFactory.getInstance().getCommandIdentity(command).getCommand();
        String page;
        try {
            page = commandInstance.execute(req);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            page = exceptionHandler.handleException(req, resp, e);
        }
        if (page.startsWith(REDIRECT)) {
            setMessagesToRedirect(req);
            resp.sendRedirect(req.getContextPath() + "/" + page.substring(REDIRECT.length()));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }

    private void setMessagesToRedirect(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute(JspConstants.ERROR_MESSAGE_ATTRIBUTE_NAME,
                req.getAttribute(JspConstants.ERROR_MESSAGE_ATTRIBUTE_NAME));
        session.setAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME,
                req.getAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME));
    }

    @Override
    public void destroy() {
        DataSource.getInstance().close();
    }
}
