package com.company.movierating.controller.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public enum Paginator {
    INSTANCE(ParametersPreparer.INSTANCE);

    private final ParametersPreparer preparer;

    Paginator(ParametersPreparer preparer) {
        this.preparer = preparer;
    }

    public Paging getPaging(HttpServletRequest req) {
        // TODO check for: negative, strange values; set limits to limit
        
        String limitStr = req.getParameter("limit");
        int limit;
        if (limitStr == null) {
            limit = 10;
        } else {
            limit = preparer.getInt(limitStr);
        }
        String pageStr = req.getParameter("page");
        long page;
        if (pageStr == null) {
            page = 1;
        } else {
            page = preparer.getLong(pageStr);
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
