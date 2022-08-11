package com.company.movierating.controller.filter;

import java.io.IOException;

import com.company.movierating.controller.util.ParametersPreparer;
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
        String command = req.getParameter("command");
        if (requiresRole(command)) {
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
                req.getRequestDispatcher("jsp/error/error.jsp").forward(req, res);
                return;
            }
            if (sessionUser.getRole() != UserDto.Role.ADMIN && sessionUser.getId() != targetId) {
                int status = 403;
                req.setAttribute("errorStatus", status);
                req.setAttribute("errorMessage", "No rights to edit other users");
                res.setStatus(status);
                req.getRequestDispatcher("index.jsp").forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private boolean requiresRole(String command) {
        return switch (command) {
            case "users", "user", "sign_in_form", "sign_in", "create_user_form", "create_user", "error", "sign_out" -> false;
            default -> true;
        };
    }

}
