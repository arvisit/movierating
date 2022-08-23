package com.company.movierating.service.util;

import com.company.movierating.dao.ScoreDao;
import com.company.movierating.dao.factory.DaoFactory;
import com.company.movierating.exception.service.ValidationException;
import com.company.movierating.service.dto.FilmDto;
import com.company.movierating.service.dto.ScoreDto;
import com.company.movierating.service.dto.UserDto;

public enum ScoreValidator {
    INSTANCE(DaoFactory.getInstance().getDao(ScoreDao.class));

    private static final String LINE_SEPARATOR = "<br>";
    private static final int MAX_NUMBER = 10;
    
    private final ScoreDao scoreDao;
    
    ScoreValidator(ScoreDao scoreDao){
        this.scoreDao = scoreDao;
    }

    public void validateScoreToCreate(ScoreDto dto) {
        StringBuilder sb = new StringBuilder();

        checkIsExisted(dto.getFilm().getId(), dto.getUser().getId(), sb);
        checkFilm(dto.getFilm(), sb);
        checkUser(dto.getUser(), sb);
        checkValue(dto.getValue(), sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new ValidationException(sb.toString());
        }
    }


    public void validateScoreToUpdate(ScoreDto dto) {
        StringBuilder sb = new StringBuilder();

        checkValue(dto.getValue(), sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new ValidationException(sb.toString());
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
        if (scoreDao.isExisted(filmId, userId)) {
            sb.append("Current user already scored current film").append(LINE_SEPARATOR);
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

    private void checkValue(Integer length, StringBuilder sb) {
        if (length == null) {
            sb.append("Length field is empty").append(LINE_SEPARATOR);
            return;
        }
        checkIfNotPositiveNumber(length, sb);
        checkForMaxValue(length, sb);
    }

    private void checkIfUser(UserDto user, StringBuilder sb) {
        if (user.getRole() != UserDto.Role.USER) {
            sb.append("Non user-level users couldn't set scores");
        }
    }

    private void checkIfNotPositiveNumber(Integer number, StringBuilder sb) {
        if (number < 1) {
            sb.append("Numeric field value should be positive").append(LINE_SEPARATOR);
        }
    }

    private void checkForMaxValue(Integer number, StringBuilder sb) {
        if (number > MAX_NUMBER) {
            sb.append("Numeric field value should be less or equal to " + MAX_NUMBER).append(LINE_SEPARATOR);
        }
    }

}
