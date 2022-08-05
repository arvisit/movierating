package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.exception.controller.NonAuthorizedException;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class EditUserFormCommand implements Command {
    private final UserService service;
    private final ParametersPreparer preparer;

    public EditUserFormCommand(UserService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new NonAuthorizedException("Authorization needed");
        }
        UserDto actor = (UserDto) session.getAttribute("user");
        UserDto subject = service.getById(preparer.getLong(req.getParameter("id")));

        UserDto approved = service.approveSubject(actor, subject);
        req.setAttribute("user", approved);
        return "jsp/edit/edit_user_form.jsp";
    }

}
