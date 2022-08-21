package com.company.movierating.controller.util;

import com.company.movierating.exception.controller.BadParameterException;
import com.company.movierating.service.dto.FilmDto;
import com.company.movierating.service.dto.UserDto;

public enum ParametersPreparer {
    INSTANCE;

    private static final String MESSAGE = "Bad parameter value '%s'";

    public long getLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new BadParameterException(String.format(MESSAGE, value));
        }
    }

    public int getInt(String value) {
        return (int) getLong(value);
    }

    public UserDto.Role getRole(String value) {
        try {
            return UserDto.Role.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BadParameterException(String.format(MESSAGE, value));
        }
    }

    public FilmDto.AgeRating getAgeRating(String value) {
        try {
            return FilmDto.AgeRating.valueOf(value.toUpperCase().replace('-', '_'));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BadParameterException(String.format(MESSAGE, value));
        }
    }
}
