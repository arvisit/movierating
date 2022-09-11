package com.company.movierating.controller.command.impl.score;

import java.util.List;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.Paginator.Paging;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.ScoreService;
import com.company.movierating.service.dto.ScoreDto;

import jakarta.servlet.http.HttpServletRequest;

public class FilmScoresCommand implements Command {
    private final ScoreService service;
    private final ParametersPreparer preparer;
    private final Paginator paginator;

    public FilmScoresCommand(ScoreService service, ParametersPreparer preparer, Paginator paginator) {
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

        List<ScoreDto> scores = service.getAllByFilm(id, limit, offset);
        long totalEntities = service.countByFilm(id);
        long fullFilledPages = totalEntities / limit;
        int partialFilledPage = (totalEntities % limit) > 0 ? 1 : 0;
        long totalPages = fullFilledPages + partialFilledPage;

        long page = paging.getPage();
        page = (page > totalPages ? totalPages : page);

        req.setAttribute("scores", scores);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("paginatedCommand", "film_scores&id=" + id);
        return JspConstants.VIEW_FILM_SCORES;
    }
}
