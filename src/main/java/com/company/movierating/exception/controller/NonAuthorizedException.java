package com.company.movierating.exception.controller;

import com.company.movierating.exception.ControllerException;

public class NonAuthorizedException extends ControllerException {

    public NonAuthorizedException(String errorMessage) {
        super(errorMessage);
    }

    public NonAuthorizedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
