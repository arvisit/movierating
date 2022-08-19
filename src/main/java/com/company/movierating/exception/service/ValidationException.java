package com.company.movierating.exception.service;

import com.company.movierating.exception.ServiceException;

public class ValidationException extends ServiceException {

    public ValidationException(String errorMessage) {
        super(errorMessage);
    }

    public ValidationException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
