package com.company.movierating.controller.filter;

import java.io.IOException;

import com.company.movierating.controller.ExceptionHandler;
import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.controller.command.factory.CommandIdentity;
import com.company.movierating.controller.util.SecurityLevel;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.service.BanService;
import com.company.movierating.service.dto.UserDto;
import com.company.movierating.service.factory.ServiceFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserSelfNotBannedLevelFilter extends HttpFilter {
    private final ExceptionHandler exceptionHandler = ExceptionHandler.INSTANCE;
    private final BanService banService = ServiceFactory.getInstance().getService(BanService.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        CommandIdentity commandIdentity = CommandFactory.getInstance().getCommandIdentity(req.getParameter("command"));
        int commandSecurityLevel = commandIdentity.getSecurityLevel().getValue();
        if (commandSecurityLevel == SecurityLevel.USER_SELF_NOT_BANNED.getValue()) {
            HttpSession session = req.getSession(false);
            UserDto sessionUser = (UserDto) session.getAttribute("user");
            if (sessionUser.getRole() != UserDto.Role.USER) {
                Exception e = new ForbiddenPageException("Should be authorized with user-level rights");
                log.error(e.getMessage(), e);
                String page = exceptionHandler.handleException(req, res, e);
                req.getRequestDispatcher(page).forward(req, res);
                return;
            }
            if (banService.isBanned(sessionUser.getId())) {
                Exception e = new ForbiddenPageException("Should have no active bans");
                log.error(e.getMessage(), e);
                String page = exceptionHandler.handleException(req, res, e);
                req.getRequestDispatcher(page).forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

}
