package com.company.movierating.controller.command.impl;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.ScoreService;
import com.company.movierating.service.dto.FilmDto;
import com.company.movierating.service.dto.ScoreDto;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class CreateScoreCommand implements Command {
    private final ScoreService scoreService;
    private final FilmService filmService;
    private final ParametersPreparer preparer;

    public CreateScoreCommand(ScoreService scoreService, FilmService filmService, ParametersPreparer preparer) {
        this.scoreService = scoreService;
        this.filmService = filmService;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String filmIdStr = req.getParameter("id");
        String valueStr = req.getParameter("value");

        FilmDto film = filmService.getById(preparer.getLong(filmIdStr));
        UserDto user = (UserDto) req.getSession(false).getAttribute("user");
        Integer value = preparer.getInt(valueStr);

        ScoreDto score = new ScoreDto();
        score.setFilm(film);
        score.setUser(user);
        score.setValue(value);

        req.setAttribute(JspConstants.LAST_PAGE_ATTRIBUTE_NAME,
                JspConstants.REDIRECT_CREATE_SCORE_FORM_COMMAND + filmIdStr);
        scoreService.create(score);
        req.setAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME, "Film was successfully scored");

        return JspConstants.REDIRECT_FILM_COMMAND + filmIdStr;
    }

}
