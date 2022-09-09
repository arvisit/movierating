package com.company.movierating.controller.command.impl;

import com.company.movierating.AppConstants;
import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
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
        req.setAttribute("user", user);
        req.setAttribute("defaultAvatar", AppConstants.DEFAULT_APP_AVATAR);
        return JspConstants.EDIT_USER_FORM;
    }

}
