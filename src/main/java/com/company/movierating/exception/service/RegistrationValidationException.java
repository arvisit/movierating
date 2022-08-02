package com.company.movierating.exception.service;

import com.company.movierating.exception.ServiceException;

@SuppressWarnings("serial")
public class RegistrationValidationException extends ServiceException {

    public RegistrationValidationException(String errorMessage) {
        super(errorMessage);
    }

    public RegistrationValidationException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
