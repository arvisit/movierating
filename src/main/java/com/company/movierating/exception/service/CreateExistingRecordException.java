package com.company.movierating.exception.service;

import com.company.movierating.exception.ServiceException;

public class CreateExistingRecordException extends ServiceException {

    public CreateExistingRecordException(String errorMessage) {
        super(errorMessage);
    }

    public CreateExistingRecordException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
