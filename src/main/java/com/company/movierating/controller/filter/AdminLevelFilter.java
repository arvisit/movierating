package com.company.movierating.controller.filter;

import java.io.IOException;

import com.company.movierating.controller.ExceptionHandler;
import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.controller.command.factory.CommandIdentity;
import com.company.movierating.controller.util.SecurityLevel;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AdminLevelFilter extends HttpFilter {
    private final ExceptionHandler exceptionHandler = ExceptionHandler.INSTANCE;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        CommandIdentity commandIdentity = CommandFactory.getInstance().getCommandIdentity(req.getParameter("command"));
        int commandSecurityLevel = commandIdentity.getSecurityLevel().getValue();
        if (commandSecurityLevel == SecurityLevel.ADMIN.getValue()) {
            HttpSession session = req.getSession(false);
            UserDto sessionUser = (UserDto) session.getAttribute("user");
            if (sessionUser.getRole() != UserDto.Role.ADMIN) {
                Exception e = new ForbiddenPageException("Admin rights level is needed");
                log.error(e.getMessage(), e);
                String page = exceptionHandler.handleException(req, res, e);
                req.getRequestDispatcher(page).forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

}
