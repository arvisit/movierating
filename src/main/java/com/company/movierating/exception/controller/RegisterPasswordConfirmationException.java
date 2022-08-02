package com.company.movierating.exception.controller;

import com.company.movierating.exception.ControllerException;

@SuppressWarnings("serial")
public class RegisterPasswordConfirmationException extends ControllerException {

    public RegisterPasswordConfirmationException(String errorMessage) {
        super(errorMessage);
    }

    public RegisterPasswordConfirmationException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
