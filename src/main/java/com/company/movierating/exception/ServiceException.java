package com.company.movierating.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String errorMessage) {
        super(errorMessage);
    }

    public ServiceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
