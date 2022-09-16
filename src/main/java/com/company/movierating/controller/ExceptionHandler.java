package com.company.movierating.controller;

import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.exception.controller.BadParameterException;
import com.company.movierating.exception.controller.NonAuthorizedException;
import com.company.movierating.exception.controller.RegisterPasswordConfirmationException;
import com.company.movierating.exception.controller.UnsupportedCommandException;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.exception.service.ValidationException;

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
            page = JspConstants.REDIRECT_ERROR;
        } else if (e instanceof BadParameterException) {
            status = 400;
            message = e.getMessage();
            page = JspConstants.REDIRECT_ERROR;
        } else if (e instanceof RegisterPasswordConfirmationException) {
            status = 400;
            message = e.getMessage();
            page = JspConstants.REDIRECT_CREATE_USER_FORM_COMMAND;
        } else if (e instanceof ValidationException) {
            status = 400;
            message = e.getMessage();
            page = (String) req.getAttribute(JspConstants.LAST_PAGE_ATTRIBUTE_NAME);
        } else if (e instanceof NonAuthorizedException) {
            status = 401;
            message = e.getMessage();
            page = JspConstants.REDIRECT_SIGN_IN_FORM_COMMAND;
        } else if (e instanceof ForbiddenPageException) {
            status = 403;
            message = e.getMessage();
            page = JspConstants.REDIRECT_MAIN_PAGE;
        } else if (e instanceof NoRecordFoundException) {
            status = 404;
            message = "Page not found";
            page = JspConstants.REDIRECT_ERROR;
        } else {
            status = 500;
            message = "Internal server error";
            page = JspConstants.REDIRECT_ERROR;
        }

        req.setAttribute("errorStatus", status);
        req.setAttribute(JspConstants.ERROR_MESSAGE_ATTRIBUTE_NAME, message);
        resp.setStatus(status);

        return page;
    }
}
