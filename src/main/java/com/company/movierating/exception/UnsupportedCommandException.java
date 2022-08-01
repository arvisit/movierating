package com.company.movierating.exception;

@SuppressWarnings("serial")
public class UnsupportedCommandException extends RuntimeException {

    public UnsupportedCommandException(String errorMessage) {
        super(errorMessage);
    }

    public UnsupportedCommandException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
