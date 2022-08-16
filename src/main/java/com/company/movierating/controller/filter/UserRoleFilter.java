package com.company.movierating.controller.filter;

import java.io.IOException;

import com.company.movierating.controller.command.factory.CommandFactory;
import com.company.movierating.controller.command.factory.CommandIdentity;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.controller.util.SecurityLevel;
import com.company.movierating.exception.controller.BadParameterException;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserRoleFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        CommandIdentity commandIdentity = CommandFactory.getInstance().getCommandIdentity(req.getParameter("command"));
        if (commandIdentity.getSecurityLevel().getValue() > SecurityLevel.GUEST.getValue()) {
            HttpSession session = req.getSession(false);
            UserDto sessionUser = (UserDto) session.getAttribute("user");
            Long targetId;
            try {
                targetId = ParametersPreparer.INSTANCE.getLong(req.getParameter("id"));
            } catch (BadParameterException e) {
                int status = 400;
                req.setAttribute("errorStatus", status);
                req.setAttribute("errorMessage", e.getMessage());
                res.setStatus(status);
                req.getRequestDispatcher(JspConstants.DEFAULT_ERROR).forward(req, res);
                return;
            }
            // TODO add check if level admin, user, self...
            if (sessionUser.getRole() != UserDto.Role.ADMIN && sessionUser.getId() != targetId) {
                int status = 403;
                req.setAttribute("errorStatus", status);
                req.setAttribute("errorMessage", "No rights to edit other users");
                res.setStatus(status);
                req.getRequestDispatcher(JspConstants.MAIN_PAGE).forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

}
