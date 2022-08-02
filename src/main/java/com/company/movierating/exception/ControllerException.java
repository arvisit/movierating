package com.company.movierating.exception;

@SuppressWarnings("serial")
public class ControllerException extends RuntimeException {

    public ControllerException(String errorMessage) {
        super(errorMessage);
    }

    public ControllerException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
