package com.company.movierating.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.company.movierating.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet("/img/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String filename = req.getPathInfo().substring(1);
            Path path = Paths.get(AppConstants.IMAGE_STORAGE_ROOT + "/" + filename);
            Files.copy(path, resp.getOutputStream());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}