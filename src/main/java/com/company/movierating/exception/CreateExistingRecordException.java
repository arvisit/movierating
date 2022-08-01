package com.company.movierating.exception;

@SuppressWarnings("serial")
public class CreateExistingRecordException extends RuntimeException {

    public CreateExistingRecordException(String errorMessage) {
        super(errorMessage);
    }

    public CreateExistingRecordException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
