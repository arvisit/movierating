package com.company.movierating.exception.service;

import com.company.movierating.exception.ServiceException;

@SuppressWarnings("serial")
public class CreateExistingRecordException extends ServiceException {

    public CreateExistingRecordException(String errorMessage) {
        super(errorMessage);
    }

    public CreateExistingRecordException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
