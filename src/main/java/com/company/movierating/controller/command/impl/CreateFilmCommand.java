package com.company.movierating.controller.command.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.company.movierating.AppConstants;
import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.FilmService;
import com.company.movierating.service.dto.FilmDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

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
        film.setTitle(title);
        film.setDescription(description);
        film.setReleaseYear(preparer.getInt(releaseYearStr));
        film.setLength(preparer.getInt(lengthStr));
        film.setAgeRating(preparer.getAgeRating(ageRatingStr));

        try {
            Part part = req.getPart("poster");
            if (part != null) {
                String initialFileName = part.getSubmittedFileName();
                String extension = initialFileName.substring(initialFileName.lastIndexOf('.'));
                String newFileName = UUID.randomUUID() + "_" + film.getId() + extension;
                String filePath = AppConstants.IMAGE_STORAGE_POSTER + "/" + newFileName;
                Path path = Paths.get(filePath);
                if (Files.notExists(path)) {
                    path = Files.createDirectories(path);
                }
                part.write(path.toString());
                film.setPoster(filePath);
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute(JspConstants.LAST_PAGE_ATTRIBUTE_NAME, "redirect:controller?command=create_film_form");
        FilmDto created = service.create(film);
        req.setAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME, "New film was successfully added");
        req.setAttribute("film", created);
        HttpSession session = req.getSession();
        session.setAttribute("film", created);
        return JspConstants.VIEW_FILM;
    }

}
