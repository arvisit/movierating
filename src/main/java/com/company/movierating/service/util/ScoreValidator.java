package com.company.movierating.service.util;

import com.company.movierating.exception.service.ValidationException;
import com.company.movierating.service.dto.FilmDto;
import com.company.movierating.service.dto.ScoreDto;
import com.company.movierating.service.dto.UserDto;

public enum ScoreValidator {
    INSTANCE;

    private static final String LINE_SEPARATOR = "<br>";
    private static final int MAX_NUMBER = 10;

    public void validateScoreToCreate(ScoreDto dto) {
        StringBuilder sb = new StringBuilder();

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
