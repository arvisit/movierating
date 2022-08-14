package com.company.movierating.controller.command.impl;

import java.util.List;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.Paginator.Paging;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.service.BanService;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.BanDto;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class AssignedBansCommand implements Command {
    private final BanService banService;
    private final UserService userService;
    private final ParametersPreparer preparer;
    private final Paginator paginator;

    public AssignedBansCommand(BanService banService, UserService userService, ParametersPreparer preparer,
            Paginator paginator) {
        this.banService = banService;
        this.userService = userService;
        this.preparer = preparer;
        this.paginator = paginator;
    }

    @Override
    public String execute(HttpServletRequest req) {
        long adminId = preparer.getLong(req.getParameter("id"));
        UserDto admin = userService.getById(adminId);
        UserDto sessionUser = (UserDto) req.getSession().getAttribute("user");
        if (admin.getRole() == sessionUser.getRole() && admin.getId() != sessionUser.getId()) {
            throw new ForbiddenPageException("You have no rights to view another admin's assigned bans list");
        }
        Paging paging = paginator.getPaging(req);
        int limit = paging.getLimit();
        long offset = paging.getOffset();

        List<BanDto> assignedBans = banService.getAllByAdmin(adminId, limit, offset);
        long totalEntities = banService.countByAdmin(adminId);
        long fullFilledPages = totalEntities / limit;
        int partialFilledPage = (totalEntities % limit) > 0 ? 1 : 0;
        long totalPages = fullFilledPages + partialFilledPage;

        long page = paging.getPage();
        page = (page > totalPages ? totalPages : page);

        req.setAttribute("bans", assignedBans);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("paginatedJsp", "bans");
        req.setAttribute("title", "Assigned Bans");
        return "jsp/view/bans.jsp";
    }

}
