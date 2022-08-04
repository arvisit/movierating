package com.company.movierating.controller;

import com.company.movierating.exception.controller.BadParameterException;
import com.company.movierating.exception.controller.NonAuthorizedException;
import com.company.movierating.exception.controller.RegisterPasswordConfirmationException;
import com.company.movierating.exception.controller.UnsupportedCommandException;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.exception.service.RegistrationValidationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public enum ExceptionHandler {
    INSTANCE;

    public String handleException(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        String page;
        int status;
        String message;

        if (e instanceof UnsupportedCommandException) {
            status = 400;
            message = e.getMessage();
            page = "jsp/error/error.jsp";
        } else if (e instanceof BadParameterException) {
            status = 400;
            message = e.getMessage();
            page = "jsp/error/error.jsp";
        } else if (e instanceof RegisterPasswordConfirmationException) {
            status = 400;
            message = e.getMessage();
            page = "jsp/create/create_user_form.jsp";
        } else if (e instanceof RegistrationValidationException) {
            status = 400;
            message = e.getMessage();
            page = "jsp/create/create_user_form.jsp";
        } else if (e instanceof NonAuthorizedException) {
            status = 401;
            message = e.getMessage();
            page = "jsp/sign_in/sign_in_form.jsp";
        } else if (e instanceof ForbiddenPageException) {
            status = 403;
            message = e.getMessage();
            page = "index.jsp";
        } else if (e instanceof NoRecordFoundException) {
            status = 404;
            message = "Page not found";
            page = "jsp/error/404.jsp";
        } else {
            status = 500;
            message = "Internal server error";
            page = "jsp/error/error.jsp";
        }

        req.setAttribute("errorStatus", status);
        req.setAttribute("errorMessage", message);
        resp.setStatus(status);

        return page;
    }
}
