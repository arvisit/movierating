package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class CreateUserCommand implements Command {
    private final UserService service;

    public CreateUserCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmedPassword = req.getParameter("confirmedPassword");

        UserDto user = new UserDto();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);

        UserDto created = service.create(user, confirmedPassword);
        req.setAttribute("successMessage", "New user was successfully registered");
        req.setAttribute("user", created);
        return "jsp/view/user.jsp";
    }

}
