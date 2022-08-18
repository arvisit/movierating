package com.company.movierating.exception.service;

import com.company.movierating.exception.ServiceException;

public class NoRecordFoundException extends ServiceException {

    public NoRecordFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NoRecordFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
