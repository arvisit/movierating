package com.company.movierating.service;

import java.util.List;

import com.company.movierating.service.dto.ScoreDto;

public interface ScoreService extends AbstractService<Long, ScoreDto> {
    List<ScoreDto> getAllByFilm(Long id, int limit, long offset);

    List<ScoreDto> getAllByUser(Long id, int limit, long offset);

    Long countByFilm(Long id);

    Long countByUser(Long id);

    Double countFilmAverageScore(Long id);

    boolean isExisted(Long filmId, Long userId);
}
