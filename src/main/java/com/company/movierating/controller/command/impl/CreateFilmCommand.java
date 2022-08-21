package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.dto.FilmDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CreateFilmCommand implements Command {
    private final FilmService service;
    private final ParametersPreparer preparer;

    public CreateFilmCommand(FilmService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String releaseYearStr = req.getParameter("releaseYear");
        String lengthStr = req.getParameter("length");
        String ageRatingStr = req.getParameter("ageRating");

        FilmDto film = new FilmDto();
        film.setTitle(title.toUpperCase());
        film.setDescription(description);
        film.setReleaseYear(preparer.getInt(releaseYearStr));
        film.setLength(preparer.getInt(lengthStr));
        film.setAgeRating(preparer.getAgeRating(ageRatingStr));

        req.setAttribute(JspConstants.LAST_PAGE_ATTRIBUTE_NAME, "redirect:controller?command=create_film_form");
        FilmDto created = service.create(film);
        req.setAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME, "New film was successfully added");
        req.setAttribute("film", created);
        HttpSession session = req.getSession();
        session.setAttribute("film", created);
        return JspConstants.VIEW_FILM;
    }

}
