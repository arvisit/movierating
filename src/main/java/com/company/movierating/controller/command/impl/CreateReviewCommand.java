package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.ReviewService;
import com.company.movierating.service.dto.FilmDto;
import com.company.movierating.service.dto.ReviewDto;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class CreateReviewCommand implements Command {
    private final ReviewService reviewService;
    private final FilmService filmService;
    private final ParametersPreparer preparer;

    public CreateReviewCommand(ReviewService reviewService, FilmService filmService, ParametersPreparer preparer) {
        this.reviewService = reviewService;
        this.filmService = filmService;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String filmIdStr = req.getParameter("id");
        String content = req.getParameter("content");

        FilmDto film = filmService.getById(preparer.getLong(filmIdStr));
        UserDto user = (UserDto) req.getSession(false).getAttribute("user");

        ReviewDto review = new ReviewDto();
        review.setFilm(film);
        review.setUser(user);
        review.setContent(content);

        req.setAttribute(JspConstants.LAST_PAGE_ATTRIBUTE_NAME,
                JspConstants.REDIRECT_CREATE_REVIEW_FORM_COMMAND + filmIdStr);
        reviewService.create(review);
        req.setAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME, "Film was successfully reviewed");

        return JspConstants.REDIRECT_FILM_COMMAND + filmIdStr;
    }

}
