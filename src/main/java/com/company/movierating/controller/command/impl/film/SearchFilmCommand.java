package com.company.movierating.controller.command.impl.film;

import java.util.List;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.Paginator.Paging;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.dto.FilmDto;

import jakarta.servlet.http.HttpServletRequest;

public class SearchFilmCommand implements Command {
    private final FilmService service;
    private final Paginator paginator;

    public SearchFilmCommand(FilmService service, Paginator paginator) {
        this.service = service;
        this.paginator = paginator;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Paging paging = paginator.getPaging(req);
        int limit = paging.getLimit();
        long offset = paging.getOffset();
        String title = req.getParameter("title");

        List<FilmDto> films = service.searchByTitle(title, limit, offset);
        long totalEntities = service.count(title);
        long fullFilledPages = totalEntities / limit;
        int partialFilledPage = (totalEntities % limit) > 0 ? 1 : 0;
        long totalPages = fullFilledPages + partialFilledPage;

        long page = paging.getPage();
        page = (page > totalPages ? totalPages : page);

        req.setAttribute("films", films);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("paginatedCommand", "search_film&title=" + title);
        return JspConstants.VIEW_FILMS;
    }
}
