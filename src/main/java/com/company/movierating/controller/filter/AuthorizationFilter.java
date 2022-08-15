package com.company.movierating.controller.filter;

import java.io.IOException;

import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.controller.command.factory.CommandIdentity;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.SecurityLevel;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthorizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        CommandIdentity commandIdentity = CommandFactory.getInstance().getCommandIdentity(req.getParameter("command"));
        if (commandIdentity.getSecurityLevel().getValue() > SecurityLevel.GUEST.getValue()) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                req.setAttribute("errorMessage", "Authorization needed");
                req.getRequestDispatcher(JspConstants.SIGN_IN_FORM).forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

}
