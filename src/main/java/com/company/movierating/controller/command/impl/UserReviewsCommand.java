package com.company.movierating.controller.command.impl;

import java.util.List;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.Paginator.Paging;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.ReviewService;
import com.company.movierating.service.dto.ReviewDto;

import jakarta.servlet.http.HttpServletRequest;

public class UserReviewsCommand implements Command {
    private final ReviewService service;
    private final ParametersPreparer preparer;
    private final Paginator paginator;

    public UserReviewsCommand(ReviewService service, ParametersPreparer preparer, Paginator paginator) {
        this.service = service;
        this.preparer = preparer;
        this.paginator = paginator;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        Long id = preparer.getLong(idStr);
        
        Paging paging = paginator.getPaging(req);
        int limit = paging.getLimit();
        long offset = paging.getOffset();

        List<ReviewDto> reviews = service.getAllByUser(id, limit, offset);
        long totalEntities = service.count();
        long fullFilledPages = totalEntities / limit;
        int partialFilledPage = (totalEntities % limit) > 0 ? 1 : 0;
        long totalPages = fullFilledPages + partialFilledPage;

        long page = paging.getPage();
        page = (page > totalPages ? totalPages : page);

        req.setAttribute("reviews", reviews);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("paginatedJsp", "reviews");
        return JspConstants.VIEW_USER_REVIEWS;
    }
}
