package com.company.movierating.exception;

@SuppressWarnings("serial")
public class NoRecordFoundException extends RuntimeException {

    public NoRecordFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NoRecordFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
