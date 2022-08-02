package com.company.movierating.exception.controller;

import com.company.movierating.exception.ControllerException;

@SuppressWarnings("serial")
public class UnsupportedCommandException extends ControllerException {

    public UnsupportedCommandException(String errorMessage) {
        super(errorMessage);
    }

    public UnsupportedCommandException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
