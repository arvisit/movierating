package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.exception.controller.RegisterPasswordConfirmationException;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class EditUserCommand implements Command {
    private final UserService service;

    public EditUserCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String id = req.getParameter("id");
        String role = req.getParameter("role");
        String reputation = req.getParameter("reputation");
        String info = req.getParameter("info");
        // TODO: add service usage
        UserDto user = new UserDto();
        user.setId(Long.parseLong(id));
        user.setRole(UserDto.Role.valueOf(role.toUpperCase()));
        user.setReputation(Integer.parseInt(reputation));
        user.setInfo(info);
//        UserDto created = service.create(user);
//        req.setAttribute("successMessage", "User was successfully updated");
//        req.setAttribute("user", created);
        req.setAttribute("successMessage", "Parameters from edit user form");
        req.setAttribute("user", user);
        return "jsp/view/user.jsp";
    }

}
