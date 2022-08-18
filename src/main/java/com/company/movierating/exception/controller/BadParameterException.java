package com.company.movierating.exception.controller;

import com.company.movierating.exception.ControllerException;

public class BadParameterException extends ControllerException {

    public BadParameterException(String errorMessage) {
        super(errorMessage);
    }

    public BadParameterException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
