package com.company.movierating.service.util;

import java.time.ZonedDateTime;

import com.company.movierating.exception.service.ValidationException;
import com.company.movierating.service.dto.BanDto;
import com.company.movierating.service.dto.UserDto;

public enum BanValidator {
    INSTANCE;

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void validateBanToCreate(BanDto dto) {
        StringBuilder sb = new StringBuilder();

        checkUser(dto.getUser(), sb);
        checkAdmin(dto.getAdmin(), sb);
        checkStartDate(dto.getStartDate(), sb);
        checkEndDate(dto.getEndDate(), dto.getStartDate(), sb);
        checkReason(dto.getReason(), sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new ValidationException(sb.toString());
        }
    }

    public void validateBanToUpdate(BanDto dto) {
        StringBuilder sb = new StringBuilder();

        checkStartDate(dto.getStartDate(), sb);
        checkEndDate(dto.getEndDate(), dto.getStartDate(), sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new ValidationException(sb.toString());
        }
    }

    private void checkUser(UserDto user, StringBuilder sb) {
        if (user == null) {
            sb.append("No user with such id").append(LINE_SEPARATOR);
            return;
        }
        if (user.getRole() != UserDto.Role.USER) {
            sb.append("Not a user role").append(LINE_SEPARATOR);
        }
    }

    private void checkAdmin(UserDto admin, StringBuilder sb) {
        if (admin == null) {
            sb.append("No admin with such id").append(LINE_SEPARATOR);
            return;
        }
        if (admin.getRole() != UserDto.Role.ADMIN) {
            sb.append("Not an admin role").append(LINE_SEPARATOR);
        }
    }

    private void checkStartDate(ZonedDateTime startDate, StringBuilder sb) {
        if (startDate == null) {
            sb.append("Start date field is empty").append(LINE_SEPARATOR);
            return;
        }
    }

    private void checkEndDate(ZonedDateTime endDate, ZonedDateTime startDate, StringBuilder sb) {
        if (endDate == null) {
            sb.append("End date field is empty").append(LINE_SEPARATOR);
            return;
        }
        if (endDate.compareTo(startDate) <= 0) {
            sb.append("End date should be greater than start date").append(LINE_SEPARATOR);
        }
    }

    private void checkReason(String reason, StringBuilder sb) {
        if (reason == null) {
            sb.append("Reason field is empty").append(LINE_SEPARATOR);
            return;
        }
    }
}
