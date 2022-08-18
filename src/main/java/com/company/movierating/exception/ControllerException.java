package com.company.movierating.exception;

public class ControllerException extends RuntimeException {

    public ControllerException(String errorMessage) {
        super(errorMessage);
    }

    public ControllerException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
