package com.company.movierating.dao;

import java.util.List;

import com.company.movierating.dao.entity.Review;

public interface ReviewDao extends AbstractDao<Long, Review> {
    List<Review> getAllByFilm(Long id, int limit, long offset);

    List<Review> getAllByUser(Long id, int limit, long offset);

    Long countByFilm(Long id);

    Long countByUser(Long id);

    boolean isExisted(Long filmId, Long userId);
}
