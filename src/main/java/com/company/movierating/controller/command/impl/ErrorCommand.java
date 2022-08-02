package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.exception.controller.UnsupportedCommandException;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {

    public ErrorCommand() {
    }

    @Override
    public String execute(HttpServletRequest req) {
        String msg = "Bad request";
        req.setAttribute("errorMessage", msg);
        throw new UnsupportedCommandException(msg);
    }

}
