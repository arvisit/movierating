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
import jakarta.servlet.http.Part;

public class EditFilmCommand implements Command {
    private final FilmService service;
    private final ParametersPreparer preparer;

    public EditFilmCommand(FilmService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String releaseYearStr = req.getParameter("releaseYear");
        String lengthStr = req.getParameter("length");
        String ageRatingStr = req.getParameter("ageRating");

        FilmDto changed = new FilmDto();

        changed.setId(preparer.getLong(idStr));
        changed.setTitle(title);
        changed.setDescription(description);
        changed.setReleaseYear(preparer.getInt(releaseYearStr));
        changed.setLength(preparer.getInt(lengthStr));
        changed.setAgeRating(preparer.getAgeRating(ageRatingStr));

        try {
            Part part = req.getPart("poster");
            if (part != null) {
                String initialFileName = part.getSubmittedFileName();
                String extension = initialFileName.substring(initialFileName.lastIndexOf('.'));
                String newFileName = UUID.randomUUID() + "_" + changed.getId() + extension;
                String filePath = AppConstants.IMAGE_STORAGE_POSTER + "/" + newFileName;
                Path path = Paths.get(filePath);
                if (Files.notExists(path)) {
                    path = Files.createDirectories(path);
                }
                part.write(path.toString());
                changed.setPoster(filePath);
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute(JspConstants.LAST_PAGE_ATTRIBUTE_NAME,
                "redirect:controller?command=edit_film_form&id=" + idStr);
        FilmDto updated = service.update(changed);
        req.setAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME, "Parameters were updated successfully");
        req.setAttribute("film", updated);
        return JspConstants.VIEW_FILM;
    }

}
