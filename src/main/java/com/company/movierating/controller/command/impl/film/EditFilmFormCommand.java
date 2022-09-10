package com.company.movierating.controller.command.impl.film;

import com.company.movierating.AppConstants;
import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.dto.FilmDto;

import jakarta.servlet.http.HttpServletRequest;

public class EditFilmFormCommand implements Command {
    private final FilmService service;
    private final ParametersPreparer preparer;

    public EditFilmFormCommand(FilmService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        FilmDto film = service.getById(preparer.getLong(req.getParameter("id")));
        req.setAttribute("film", film);
        req.setAttribute("defaultPoster", AppConstants.DEFAULT_APP_POSTER);
        return JspConstants.EDIT_FILM_FORM;
    }

}
