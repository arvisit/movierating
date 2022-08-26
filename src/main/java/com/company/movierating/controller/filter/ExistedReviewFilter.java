package com.company.movierating.controller.filter;

import java.io.IOException;

import com.company.movierating.controller.ExceptionHandler;
import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.controller.command.factory.CommandIdentity;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.exception.controller.BadParameterException;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.service.ReviewService;
import com.company.movierating.service.dto.UserDto;
import com.company.movierating.service.factory.ServiceFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ExistedReviewFilter extends HttpFilter {
    private final ExceptionHandler exceptionHandler = ExceptionHandler.INSTANCE;
    private final ReviewService reviewService = ServiceFactory.getInstance().getService(ReviewService.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        CommandIdentity commandIdentity = CommandFactory.getInstance().getCommandIdentity(req.getParameter("command"));
        if (commandIdentity == CommandIdentity.CREATE_REVIEW || commandIdentity == CommandIdentity.CREATE_REVIEW_FORM) {
            Long filmId;
            try {
                filmId = ParametersPreparer.INSTANCE.getLong(req.getParameter("id"));
            } catch (BadParameterException e) {
                log.error(e.getMessage(), e);
                String page = exceptionHandler.handleException(req, res, e);
                req.getRequestDispatcher(page).forward(req, res);
                return;
            }
            Long userId = ((UserDto) req.getSession(false).getAttribute("user")).getId();
            
            if (reviewService.isExisted(filmId, userId)) {
                Exception e = new ForbiddenPageException("You already has reviewed current film");
                log.error(e.getMessage(), e);
                String page = exceptionHandler.handleException(req, res, e);
                req.getRequestDispatcher(page).forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

}
