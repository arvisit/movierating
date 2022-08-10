package com.company.movierating.exception.service;

import com.company.movierating.exception.ServiceException;

@SuppressWarnings("serial")
public class CreateValidationException extends ServiceException {

    public CreateValidationException(String errorMessage) {
        super(errorMessage);
    }

    public CreateValidationException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
