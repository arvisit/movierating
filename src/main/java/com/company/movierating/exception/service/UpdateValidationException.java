package com.company.movierating.exception.service;

import com.company.movierating.exception.ServiceException;

public class UpdateValidationException extends ServiceException {

    public UpdateValidationException(String errorMessage) {
        super(errorMessage);
    }

    public UpdateValidationException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
