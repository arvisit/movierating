package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class EditUserFormCommand implements Command {
    private final UserService service;

    public EditUserFormCommand(UserService service) {
        this.service = service;
    }
    
    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("id"));
        UserDto user = service.getById(id);
        req.setAttribute("user", user);
        return "jsp/edit/edit_user_form.jsp";
    }

}
