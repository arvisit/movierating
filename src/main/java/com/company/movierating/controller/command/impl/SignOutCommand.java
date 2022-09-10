package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SignOutCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        session.invalidate();
        return JspConstants.REDIRECT_MAIN_PAGE;
    }
    
}
