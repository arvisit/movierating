package com.company.movierating.controller.filter;

import java.io.IOException;

import com.company.movierating.controller.ExceptionHandler;
import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.controller.command.factory.CommandIdentity;
import com.company.movierating.controller.util.SecurityLevel;
import com.company.movierating.exception.controller.NonAuthorizedException;
import com.company.movierating.exception.controller.UnsupportedCommandException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthorizationFilter extends HttpFilter {
    private final ExceptionHandler exceptionHandler = ExceptionHandler.INSTANCE;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        CommandIdentity commandIdentity;
        try {
            commandIdentity = CommandFactory.getInstance().getCommandIdentity(req.getParameter("command"));
        } catch (UnsupportedCommandException e) {
            String page = exceptionHandler.handleException(req, res, e);
            req.getRequestDispatcher(page).forward(req, res);
            return;
        }
        if (commandIdentity.getSecurityLevel().getValue() > SecurityLevel.GUEST.getValue()) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                Exception e = new NonAuthorizedException("Authorization needed");
                log.error(e.getMessage(), e);
                String page = exceptionHandler.handleException(req, res, e);
                req.getRequestDispatcher(page).forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

}
