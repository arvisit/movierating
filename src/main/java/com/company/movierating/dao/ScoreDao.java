package com.company.movierating.dao;

import java.util.List;

import com.company.movierating.dao.entity.Score;

public interface ScoreDao extends AbstractDao<Long, Score> {
    List<Score> getAllByFilm(Long id, int limit, long offset);

    List<Score> getAllByUser(Long id, int limit, long offset);

    Long countByFilm(Long id);

    Long countByUser(Long id);

    Double getFilmAverageScore(Long id);
}
