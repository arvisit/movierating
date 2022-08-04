package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;

import jakarta.servlet.http.HttpServletRequest;

public class SignInFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/sign_in/sign_in_form.jsp";
    }
    
}
