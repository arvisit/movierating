package com.company.movierating.exception.service;

import com.company.movierating.exception.ServiceException;

@SuppressWarnings("serial")
public class ForbiddenPageException extends ServiceException {

    public ForbiddenPageException(String errorMessage) {
        super(errorMessage);
    }

    public ForbiddenPageException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
