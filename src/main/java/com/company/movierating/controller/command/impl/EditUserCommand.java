package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.UserParametersPreparer;
import com.company.movierating.exception.controller.NonAuthorizedException;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class EditUserCommand implements Command {
    private final UserService service;
    private final UserParametersPreparer preparer;

    public EditUserCommand(UserService service, UserParametersPreparer preparer) {
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

        String idStr = req.getParameter("id");
        String roleStr = req.getParameter("role");
        String reputationStr = req.getParameter("reputation");
        String info = req.getParameter("info");
        String email = req.getParameter("email");

        UserDto changed = service.getById(actor, preparer.getLong(idStr));

        changed.setRole(preparer.getRole(roleStr));
        changed.setReputation(preparer.getInt(reputationStr));
        changed.setInfo(info);
        changed.setEmail(email);

        UserDto updated = service.update(changed);
        req.setAttribute("successMessage", "Parameters from edit user form");
        req.setAttribute("user", updated);
        return "jsp/view/user.jsp";
    }

}
