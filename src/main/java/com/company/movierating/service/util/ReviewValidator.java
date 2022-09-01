package com.company.movierating.service.util;

import com.company.movierating.dao.ReviewDao;
import com.company.movierating.dao.ScoreDao;
import com.company.movierating.dao.connection.ConfigurationManager;
import com.company.movierating.dao.factory.DaoFactory;
import com.company.movierating.exception.service.ValidationException;
import com.company.movierating.service.dto.FilmDto;
import com.company.movierating.service.dto.ReviewDto;
import com.company.movierating.service.dto.UserDto;

public enum ReviewValidator {
    INSTANCE(DaoFactory.getInstance().getDao(ReviewDao.class), DaoFactory.getInstance().getDao(ScoreDao.class),
            ConfigurationManager.getInstance());

    private static final String LINE_SEPARATOR = "<br>";
    private final int maxLength;

    private final ReviewDao reviewDao;
    private final ScoreDao scoreDao;

    ReviewValidator(ReviewDao reviewDao, ScoreDao scoreDao, ConfigurationManager properties) {
        this.reviewDao = reviewDao;
        this.scoreDao = scoreDao;
        this.maxLength = properties.getReviewMaxLength();
    }

    public void validateReviewToCreate(ReviewDto dto) {
        StringBuilder sb = new StringBuilder();

        checkIsScoreExisted(dto.getFilm().getId(), dto.getUser().getId(), sb);
        checkIsExisted(dto.getFilm().getId(), dto.getUser().getId(), sb);
        checkFilm(dto.getFilm(), sb);
        checkUser(dto.getUser(), sb);
        checkContent(dto.getContent(), sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new ValidationException(sb.toString());
        }
    }

    public void validateReviewToUpdate(ReviewDto dto) {
        StringBuilder sb = new StringBuilder();

        checkContent(dto.getContent(), sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new ValidationException(sb.toString());
        }
    }

    private void checkIsScoreExisted(Long filmId, Long userId, StringBuilder sb) {
        if (filmId == null) {
            sb.append("Film id is empty").append(LINE_SEPARATOR);
        }
        if (userId == null) {
            sb.append("User id is empty").append(LINE_SEPARATOR);
            return;
        }
        if (!scoreDao.isExisted(filmId, userId)) {
            sb.append("Current user didn't score current film").append(LINE_SEPARATOR);
        }
    }
    
    private void checkIsExisted(Long filmId, Long userId, StringBuilder sb) {
        if (filmId == null) {
            sb.append("Film id is empty").append(LINE_SEPARATOR);
        }
        if (userId == null) {
            sb.append("User id is empty").append(LINE_SEPARATOR);
            return;
        }
        if (reviewDao.isExisted(filmId, userId)) {
            sb.append("Current user already reviewed current film").append(LINE_SEPARATOR);
        }
    }

    private void checkFilm(FilmDto film, StringBuilder sb) {
        if (film == null) {
            sb.append("Film field is empty").append(LINE_SEPARATOR);
            return;
        }
    }

    private void checkUser(UserDto user, StringBuilder sb) {
        if (user == null) {
            sb.append("User field is empty").append(LINE_SEPARATOR);
            return;
        }
        checkIfUser(user, sb);
    }

    private void checkContent(String content, StringBuilder sb) {
        if (content == null) {
            sb.append("Content field is empty").append(LINE_SEPARATOR);
            return;
        }
        if (content.isBlank()) {
            sb.append("Review should contain text and not to consit of whitespace characters").append(LINE_SEPARATOR);
            return;
        }
        checkIfEmpty(content, sb);
        checkForMaxLength(content, sb);
    }

    private void checkIfUser(UserDto user, StringBuilder sb) {
        if (user.getRole() != UserDto.Role.USER) {
            sb.append("Non user-level users couldn't submit a review");
        }
    }

    private void checkIfEmpty(String str, StringBuilder sb) {
        if (str.isEmpty()) {
            sb.append("Review shouldn't be empty").append(LINE_SEPARATOR);
        }
    }

    private void checkForMaxLength(String str, StringBuilder sb) {
        if (str.length() > maxLength) {
            sb.append("Review should be less or equal to " + maxLength).append(LINE_SEPARATOR);
        }
    }

}
