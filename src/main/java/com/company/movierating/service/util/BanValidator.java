package com.company.movierating.service.util;

import java.time.ZonedDateTime;

import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.entity.User;
import com.company.movierating.dao.factory.DaoFactory;
import com.company.movierating.exception.service.CreateValidationException;
import com.company.movierating.exception.service.UpdateValidationException;
import com.company.movierating.service.dto.BanDto;

public enum BanValidator {
    INSTANCE;

    private static final UserDao USER_DAO = DaoFactory.getInstance().getDao(UserDao.class);
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void validateBanToCreate(BanDto dto) {
        StringBuilder sb = new StringBuilder();

        checkUserId(dto.getUserId(), sb);
        checkAdminId(dto.getAdminId(), sb);
        checkStartDate(dto.getStartDate(), sb);
        checkEndDate(dto.getEndDate(), dto.getStartDate(), sb);
        checkReason(dto.getReason(), sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new CreateValidationException(sb.toString());
        }
    }

    public void validateBanToUpdate(BanDto dto) {
        StringBuilder sb = new StringBuilder();

        checkStartDate(dto.getStartDate(), sb);
        checkEndDate(dto.getEndDate(), dto.getStartDate(), sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new UpdateValidationException(sb.toString());
        }
    }

    private void checkUserId(Long id, StringBuilder sb) {
        if (id == null) {
            sb.append("User id field is empty").append(LINE_SEPARATOR);
            return;
        }
        User user = USER_DAO.getById(id);
        if (user == null) {
            sb.append("No user with such id").append(LINE_SEPARATOR);
            return;
        }
        if (user.getRole() != User.Role.USER) {
            sb.append("Not a user role").append(LINE_SEPARATOR);
        }
    }

    private void checkAdminId(Long id, StringBuilder sb) {
        if (id == null) {
            sb.append("Admin id field is empty").append(LINE_SEPARATOR);
            return;
        }
        User admin = USER_DAO.getById(id);
        if (admin == null) {
            sb.append("No admin with such id").append(LINE_SEPARATOR);
            return;
        }
        if (admin.getRole() != User.Role.ADMIN) {
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
