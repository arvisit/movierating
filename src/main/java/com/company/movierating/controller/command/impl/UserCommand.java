package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.exception.controller.BadParameterException;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class UserCommand implements Command {
    private final UserService service;

    public UserCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        if (idStr == null) {
            throw new BadParameterException("Parameter id is empty");
        }
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new BadParameterException("Bad value '" + idStr + "' for parameter id");
        }
        UserDto user = service.getById(id);
        req.setAttribute("user", user);
        return "jsp/view/user.jsp";
    }

}
