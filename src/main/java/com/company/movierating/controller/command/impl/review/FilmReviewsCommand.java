package com.company.movierating.controller.command.impl.review;

import java.util.List;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.Paginator;
import com.company.movierating.controller.util.Paginator.Paging;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.ReviewService;
import com.company.movierating.service.dto.FilmDto;
import com.company.movierating.service.dto.ReviewDto;

import jakarta.servlet.http.HttpServletRequest;

public class FilmReviewsCommand implements Command {
    private final ReviewService reviewService;
    private final FilmService filmService;
    private final ParametersPreparer preparer;
    private final Paginator paginator;

    public FilmReviewsCommand(ReviewService reviewService, FilmService filmService, ParametersPreparer preparer,
            Paginator paginator) {
        this.reviewService = reviewService;
        this.filmService = filmService;
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

        List<ReviewDto> reviews = reviewService.getAllByFilm(id, limit, offset);
        FilmDto film = filmService.getById(id);
        long totalEntities = reviewService.countByFilm(id);
        long fullFilledPages = totalEntities / limit;
        int partialFilledPage = (totalEntities % limit) > 0 ? 1 : 0;
        long totalPages = fullFilledPages + partialFilledPage;

        long page = paging.getPage();
        page = (page > totalPages ? totalPages : page);

        req.setAttribute("film", film);
        req.setAttribute("reviews", reviews);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute(JspConstants.PAGINATED_COMMAND_ATTRIBUTE_NAME, "film_reviews&id=" + id);
        return JspConstants.VIEW_FILM_REVIEWS;
    }
}
