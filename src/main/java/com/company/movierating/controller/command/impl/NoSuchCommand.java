package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.exception.controller.UnsupportedCommandException;

import jakarta.servlet.http.HttpServletRequest;

public class NoSuchCommand implements Command {

    public NoSuchCommand() {
    }

    @Override
    public String execute(HttpServletRequest req) {
        throw new UnsupportedCommandException("Unknown command: " + req.getParameter("command"));
    }

}
