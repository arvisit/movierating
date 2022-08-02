package com.company.movierating.service.validation;

import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.entity.User;
import com.company.movierating.dao.factory.DaoFactory;
import com.company.movierating.exception.service.RegistrationValidationException;
import com.company.movierating.service.dto.UserDto;

public class UserValidator {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final int LOGIN_MAX_LENGTH = 100;
    private static final int LOGIN_MIN_LENGTH = 4;
    private static final int PASSWORD_MAX_LENGTH = 32;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int EMAIL_MAX_LENGTH = 100;
    private static final int EMAIL_MIN_LENGTH = 5;
    private static final UserDao USER_DAO = DaoFactory.getInstance().getDao(UserDao.class);

    public static void validateUserToCreate(UserDto entity) {
        StringBuilder sb = new StringBuilder();

        checkEmail(entity, sb);
        checkLogin(entity, sb);
        checkPassword(entity, sb);

        if (sb.length() != 0) {
            sb.delete(sb.length() - LINE_SEPARATOR.length(), sb.length());
            throw new RegistrationValidationException(sb.toString());
        }
    }

    private static void checkPassword(UserDto entity, StringBuilder sb) {
        String password = entity.getPassword();
        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            sb.append("Password length should be in range between " + PASSWORD_MIN_LENGTH + " and " //
                    + PASSWORD_MAX_LENGTH).append(LINE_SEPARATOR);
        }
        if (password.toUpperCase().equals(password) || password.toLowerCase().equals(password)) {
            sb.append("Password should not consist only from upper or lower case letters").append(LINE_SEPARATOR);
        }
        if (!password.matches(".*[A-Z].*")) {
            sb.append("Password should contain at least one upper case letter").append(LINE_SEPARATOR);
        }
        if (!password.matches(".*[a-z].*")) {
            sb.append("Password should contain at least one lower case letter").append(LINE_SEPARATOR);
        }
        if (!password.matches(".*[0-9].*")) {
            sb.append("Password should contain at least one decimal digit").append(LINE_SEPARATOR);
        }
    }

    private static void checkLogin(UserDto entity, StringBuilder sb) {
        String login = entity.getLogin();
        if (!login.matches("[A-Za-z0-9_]{" + LOGIN_MIN_LENGTH + "," + LOGIN_MAX_LENGTH + "}")) {
            sb.append("Login should consist of word characters - latin letters, underscore, decimal digits")
                    .append(LINE_SEPARATOR);
        }
        User existing = USER_DAO.getByLogin(login);
        if (existing != null) {
            sb.append("User with such login already exists").append(LINE_SEPARATOR);
        }
    }

    private static void checkEmail(UserDto entity, StringBuilder sb) {
        String email = entity.getEmail();
        if (!email.matches("[A-Za-z0-9_\\.]+@[A-Za-z0-9_\\.]+\\.[A-Za-z0-9_]+")) {
            sb.append("Invalid email value: " + email).append(LINE_SEPARATOR);
        }
        if (email.length() < EMAIL_MIN_LENGTH || email.length() > EMAIL_MAX_LENGTH) {
            sb.append("Email length should be in range between " + EMAIL_MIN_LENGTH + " and " + EMAIL_MAX_LENGTH)
                    .append(LINE_SEPARATOR);
        }
        User existing = USER_DAO.getByEmail(entity.getEmail());
        if (existing != null) {
            sb.append("User with such email already exists").append(LINE_SEPARATOR);
        }
    }
}
