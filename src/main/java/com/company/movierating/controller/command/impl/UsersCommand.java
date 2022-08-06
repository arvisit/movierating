package com.company.movierating.controller.command.impl;

import java.util.List;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.Paginator.Paging;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class UsersCommand implements Command {
    private final UserService service;
    private final Paginator paginator;

    public UsersCommand(UserService service, Paginator paginator) {
        this.service = service;
        this.paginator = paginator;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Paging paging = paginator.getPaging(req);
        
        List<UserDto> users = service.getAll(paging.getLimit(), paging.getOffset());
        long totalEntities = service.count();
        long fullFilledPages = totalEntities / paging.getLimit();
        int partialFilledPage = (totalEntities % paging.getLimit()) > 0 ? 1 : 0;
        long totalPages = fullFilledPages + partialFilledPage;
        
        req.setAttribute("users", users);
        req.setAttribute("currentPage", paging.getPage());
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("paginatedJsp", "users");
        return "jsp/view/users.jsp";
    }
}
