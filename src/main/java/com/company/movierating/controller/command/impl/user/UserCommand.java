package com.company.movierating.controller.command.impl.user;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class UserCommand implements Command {
    private final UserService service;
    private final ParametersPreparer preparer;

    public UserCommand(UserService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        long id = preparer.getLong(req.getParameter("id"));
        UserDto user = service.getById(id);
        req.setAttribute("user", user);
        return JspConstants.VIEW_USER;
    }

}
