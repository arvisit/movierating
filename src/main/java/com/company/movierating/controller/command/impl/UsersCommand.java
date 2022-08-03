package com.company.movierating.controller.command.impl;

import java.util.List;

import com.company.movierating.controller.command.Command;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class UsersCommand implements Command {
    private final UserService service;

    public UsersCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<UserDto> users;
        users = service.getAll();
        req.setAttribute("users", users);
        return "jsp/view/users.jsp";
    }
}
