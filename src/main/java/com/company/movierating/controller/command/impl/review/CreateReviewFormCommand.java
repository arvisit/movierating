package com.company.movierating.controller.command.impl.review;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.dto.FilmDto;

import jakarta.servlet.http.HttpServletRequest;

public class CreateReviewFormCommand implements Command {
    private final FilmService service;
    private final ParametersPreparer preparer;

    public CreateReviewFormCommand(FilmService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        FilmDto film = service.getById(preparer.getLong(req.getParameter("id")));
        req.setAttribute("film", film);
        return JspConstants.CREATE_REVIEW_FORM;
    }

}
