package com.company.movierating.controller.command.impl.user;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;

import jakarta.servlet.http.HttpServletRequest;

public class CreateUserFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return JspConstants.CREATE_USER_FORM;
    }

}
