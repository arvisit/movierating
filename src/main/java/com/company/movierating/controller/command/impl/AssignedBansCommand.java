package com.company.movierating.controller.command.impl;

import java.util.List;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.Paginator.Paging;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.BanService;
import com.company.movierating.service.dto.BanDto;

import jakarta.servlet.http.HttpServletRequest;

public class AssignedBansCommand implements Command {
    private final BanService banService;
    private final ParametersPreparer preparer;
    private final Paginator paginator;

    public AssignedBansCommand(BanService banService, ParametersPreparer preparer,
            Paginator paginator) {
        this.banService = banService;
        this.preparer = preparer;
        this.paginator = paginator;
    }

    @Override
    public String execute(HttpServletRequest req) {
        long adminId = preparer.getLong(req.getParameter("id"));
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
        return JspConstants.VIEW_BANS;
    }

}
