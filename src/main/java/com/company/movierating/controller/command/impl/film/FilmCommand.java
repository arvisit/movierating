package com.company.movierating.controller.command.impl.film;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.dto.FilmDto;

import jakarta.servlet.http.HttpServletRequest;

public class FilmCommand implements Command {
    private final FilmService service;
    private final ParametersPreparer preparer;

    public FilmCommand(FilmService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        long id = preparer.getLong(req.getParameter("id"));
        FilmDto film = service.getById(id);
        req.setAttribute("film", film);
        return JspConstants.VIEW_FILM;
    }

}
