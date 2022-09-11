package com.company.movierating.dao;

import java.util.List;

import com.company.movierating.dao.entity.Film;

public interface FilmDao extends AbstractDao<Long, Film> {
    List<Film> searchByTitle(String title, int limit, long offset);

    Long count(String title);
}
