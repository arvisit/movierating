package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SignOutCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        session.setAttribute("user", null);
        return "index.jsp";
    }
    
}
