package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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

        req.setAttribute(JspConstants.LAST_PAGE_ATTRIBUTE_NAME, "redirect:controller?command=create_user_form");
        UserDto created = service.create(user, confirmedPassword);
        req.setAttribute(JspConstants.ERROR_MESSAGE_ATTRIBUTE_NAME, "New user was successfully registered");
        req.setAttribute("user", created);
        HttpSession session = req.getSession();
        session.setAttribute("user", created);
        return JspConstants.VIEW_USER;
    }

}
