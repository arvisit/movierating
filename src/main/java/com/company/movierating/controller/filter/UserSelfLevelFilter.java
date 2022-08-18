package com.company.movierating.controller.filter;

import java.io.IOException;

import com.company.movierating.controller.ExceptionHandler;
import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.controller.command.factory.CommandIdentity;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.controller.util.SecurityLevel;
import com.company.movierating.exception.controller.BadParameterException;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.service.UserService;
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
public class UserSelfLevelFilter extends HttpFilter {
    private final ExceptionHandler exceptionHandler = ExceptionHandler.INSTANCE;
    private final UserService userService = ServiceFactory.getInstance().getService(UserService.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        CommandIdentity commandIdentity = CommandFactory.getInstance().getCommandIdentity(req.getParameter("command"));
        int commandSecurityLevel = commandIdentity.getSecurityLevel().getValue();
        if (commandSecurityLevel == SecurityLevel.USER_SELF.getValue()) {
            Long targetId;
            try {
                targetId = ParametersPreparer.INSTANCE.getLong(req.getParameter("id"));
            } catch (BadParameterException e) {
                log.error(e.getMessage(), e);
                String page = exceptionHandler.handleException(req, res, e);
                req.getRequestDispatcher(page).forward(req, res);
                return;
            }
            HttpSession session = req.getSession(false);
            UserDto sessionUser = (UserDto) session.getAttribute("user");
            if (sessionUser.getRole() != UserDto.Role.ADMIN && sessionUser.getId() != targetId) {
                Exception e = new ForbiddenPageException("No rights to access another user's data");
                log.error(e.getMessage(), e);
                String page = exceptionHandler.handleException(req, res, e);
                req.getRequestDispatcher(page).forward(req, res);
                return;
            }
            UserDto targetUser = userService.getById(targetId);
            if (sessionUser.getRole() == UserDto.Role.ADMIN && targetUser.getRole() == UserDto.Role.ADMIN
                    && sessionUser.getId() != targetId) {
                Exception e = new ForbiddenPageException("No rights to access another admnin's data");
                log.error(e.getMessage(), e);
                String page = exceptionHandler.handleException(req, res, e);
                req.getRequestDispatcher(page).forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

}
