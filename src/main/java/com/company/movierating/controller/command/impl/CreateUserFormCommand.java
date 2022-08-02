package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

public class CreateUserFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/create/create_user_form.jsp";
    }

}
