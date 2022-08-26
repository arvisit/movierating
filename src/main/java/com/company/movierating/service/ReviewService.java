package com.company.movierating.service;

import java.util.List;

import com.company.movierating.service.dto.ReviewDto;

public interface ReviewService extends AbstractService<Long, ReviewDto> {
    List<ReviewDto> getAllByFilm(Long id, int limit, long offset);

    List<ReviewDto> getAllByUser(Long id, int limit, long offset);

    Long countByFilm(Long id);

    Long countByUser(Long id);

    boolean isExisted(Long filmId, Long userId);
}
