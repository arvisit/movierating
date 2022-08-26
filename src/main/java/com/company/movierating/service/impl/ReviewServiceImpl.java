package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.ReviewDao;
import com.company.movierating.dao.entity.Review;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.service.ReviewService;
import com.company.movierating.service.converter.impl.ReviewConverter;
import com.company.movierating.service.dto.ReviewDto;
import com.company.movierating.service.util.ReviewValidator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReviewServiceImpl implements ReviewService {
    private final ReviewDao reviewDao;
    private final ReviewConverter reviewConverter;
    private final ReviewValidator reviewValidator;

    public ReviewServiceImpl(ReviewDao reviewDao, ReviewConverter reviewConverter, ReviewValidator reviewValidator) {
        this.reviewDao = reviewDao;
        this.reviewConverter = reviewConverter;
        this.reviewValidator = reviewValidator;
    }

    @Override
    public ReviewDto getById(Long id) {
        log.debug("Review service method _getById_ was called");
        Review entity = reviewDao.getById(id);
        if (entity == null) {
            throw new NoRecordFoundException("There is no review with id= " + id);
        }
        return reviewConverter.toDto(entity);

    }

    @Override
    public List<ReviewDto> getAll() {
        log.debug("Review service method _getAll_ was called");
        return reviewDao.getAll().stream() //
                .map(reviewConverter::toDto) //
                .toList();
    }

    @Override
    public List<ReviewDto> getAll(int limit, long offset) {
        log.debug("Review service method _getAll_ (paged) was called");
        return reviewDao.getAll(limit, offset).stream() //
                .map(reviewConverter::toDto) //
                .toList();
    }

    @Override
    public List<ReviewDto> getAllByFilm(Long id, int limit, long offset) {
        log.debug("Review service method _getAllByFilm_ (paged) was called");
        return reviewDao.getAllByFilm(id, limit, offset).stream() //
                .map(reviewConverter::toDto) //
                .toList();
    }

    @Override
    public List<ReviewDto> getAllByUser(Long id, int limit, long offset) {
        log.debug("Review service method _getAllByUser_ (paged) was called");
        return reviewDao.getAllByUser(id, limit, offset).stream() //
                .map(reviewConverter::toDto) //
                .toList();
    }

    @Override
    public ReviewDto create(ReviewDto dto) {
        log.debug("Review service method _create_ was called");
        reviewValidator.validateReviewToCreate(dto);
        Review createdEntity = reviewDao.create(reviewConverter.toEntity(dto));
        return reviewConverter.toDto(createdEntity);
    }

    @Override
    public ReviewDto update(ReviewDto dto) {
        log.debug("Review service method _update_ was called");
        reviewValidator.validateReviewToUpdate(dto);
        Review updatedEntity = reviewDao.update(reviewConverter.toEntity(dto));
        return reviewConverter.toDto(updatedEntity);
    }

    @Override
    public boolean delete(Long id) {
        log.debug("Review service method _delete_ was called");
        return reviewDao.delete(id);
    }

    @Override
    public Long count() {
        log.debug("Review service method _count_ was called");
        return reviewDao.count();
    }

    @Override
    public Long countByFilm(Long id) {
        log.debug("Review service method _countByFilm_ was called");
        return reviewDao.countByFilm(id);
    }

    @Override
    public Long countByUser(Long id) {
        log.debug("Review service method _countByUser_ was called");
        return reviewDao.countByUser(id);
    }

    @Override
    public boolean isExisted(Long filmId, Long userId) {
        log.debug("Review service method _isExisted_ was called");
        return reviewDao.isExisted(filmId, userId);
    }

}
