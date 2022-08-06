package com.company.movierating.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public enum Paginator {
    INSTANCE(ParametersPreparer.INSTANCE);

    private final ParametersPreparer preparer;
    private final static int LIMIT = 10;

    Paginator(ParametersPreparer preparer) {
        this.preparer = preparer;
    }

    public Paging getPaging(HttpServletRequest req) {
        String limitStr = req.getParameter("limit");
        int limit;
        if (limitStr == null) {
            limit = LIMIT;
        } else {
            limit = preparer.getInt(limitStr);
        }
        if (limit != LIMIT) {
            limit = LIMIT;
        }

        String pageStr = req.getParameter("page");
        long page;
        if (pageStr == null) {
            page = 1;
        } else {
            page = preparer.getLong(pageStr);
        }
        if (page < 0) {
            page = 1;
        }

        long offset = (page - 1) * limit;
        return new Paging(limit, offset, page);
    }

    @RequiredArgsConstructor
    public static class Paging {
        @Getter
        private final int limit;
        @Getter
        private final long offset;
        @Getter
        private final long page;
    }
}
