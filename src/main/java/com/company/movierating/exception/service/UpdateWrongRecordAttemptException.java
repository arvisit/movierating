package com.company.movierating.exception.service;

import com.company.movierating.exception.ServiceException;

@SuppressWarnings("serial")
public class UpdateWrongRecordAttemptException extends ServiceException {

    public UpdateWrongRecordAttemptException(String errorMessage) {
        super(errorMessage);
    }

    public UpdateWrongRecordAttemptException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
