package com.company.movierating.service.util;

import com.company.movierating.exception.service.ValidationException;
import com.company.movierating.service.dto.FilmDto;
import com.company.movierating.service.dto.FilmDto.AgeRating;

public enum FilmValidator {
    INSTANCE;

    private static final String LINE_SEPARATOR = "<br>";
    private static final int MAX_NUMBER = Short.MAX_VALUE;

    public void validateFilm(FilmDto dto) {
        StringBuilder sb = new StringBuilder();

        checkTitle(dto.getTitle(), sb);
        checkDescription(dto.getDescription(), sb);
        checkReleaseYear(dto.getReleaseYear(), sb);
        checkLength(dto.getLength(), sb);
        checkAgeRating(dto.getAgeRating(), sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new ValidationException(sb.toString());
        }
    }

    private void checkTitle(String title, StringBuilder sb) {
        nullCheck(title, sb, "Title field is empty");
    }

    private void checkDescription(String description, StringBuilder sb) {
        nullCheck(description, sb, "Description field is empty");
    }

    private void checkReleaseYear(Integer releaseYear, StringBuilder sb) {
        if (releaseYear == null) {
            sb.append("Release year field is empty").append(LINE_SEPARATOR);
            return;
        }
        checkIfNotPositiveNumber(releaseYear, sb);
        checkForMaxValue(releaseYear, sb);
    }

    private void checkLength(Integer length, StringBuilder sb) {
        if (length == null) {
            sb.append("Length field is empty").append(LINE_SEPARATOR);
            return;
        }
        checkIfNotPositiveNumber(length, sb);
        checkForMaxValue(length, sb);
    }

    private void checkAgeRating(AgeRating ageRating, StringBuilder sb) {
        nullCheck(ageRating, sb, "Age rating field is empty");
    }

    private <T> void nullCheck(T input, StringBuilder sb, String errorMessage) {
        if (input == null) {
            sb.append(errorMessage).append(LINE_SEPARATOR);
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
