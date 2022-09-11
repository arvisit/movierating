package com.company.movierating.service;

import java.util.List;

import com.company.movierating.service.dto.FilmDto;

public interface FilmService extends AbstractService<Long, FilmDto> {
    List<FilmDto> searchByTitle(String title, int limit, long offset);

    Long count(String title);
}
