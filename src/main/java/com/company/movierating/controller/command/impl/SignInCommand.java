package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SignInCommand implements Command {
    private final UserService service;

    public SignInCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDto user = service.signIn(login, password);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        return JspConstants.REDIRECT_MAIN_PAGE;
    }

}
