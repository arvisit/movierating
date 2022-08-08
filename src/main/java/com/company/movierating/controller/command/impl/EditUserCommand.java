package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class EditUserCommand implements Command {
    private final UserService service;
    private final ParametersPreparer preparer;

    public EditUserCommand(UserService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        String roleStr = req.getParameter("role");
        String reputationStr = req.getParameter("reputation");
        String info = req.getParameter("info");
        String email = req.getParameter("email");

        UserDto changed = new UserDto();

        changed.setId(preparer.getLong(idStr));
        changed.setRole(preparer.getRole(roleStr));
        changed.setReputation(preparer.getInt(reputationStr));
        changed.setInfo(info);
        changed.setEmail(email);

        UserDto updated = service.update(changed);
        req.setAttribute("successMessage", "Parameters were updated successfully");
        req.setAttribute("user", updated);
        return "jsp/view/user.jsp";
    }

}
