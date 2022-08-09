package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class EditUserFormCommand implements Command {
    private final UserService service;
    private final ParametersPreparer preparer;

    public EditUserFormCommand(UserService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        UserDto user = service.getById(preparer.getLong(req.getParameter("id")));
        UserDto sessionUser = (UserDto) req.getSession().getAttribute("user");
        if (user.getRole() == sessionUser.getRole() && user.getId() != sessionUser.getId()) {
            throw new ForbiddenPageException("You have no rights to edit another admin");
        }
        req.setAttribute("user", user);
        return "jsp/edit/edit_user_form.jsp";
    }

}
