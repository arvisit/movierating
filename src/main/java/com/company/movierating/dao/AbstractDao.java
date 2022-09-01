package com.company.movierating.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AbstractDao<K, T> {
    T getById(K id);

    T getById(K id, Connection connection) throws SQLException;

    List<T> getAll();

    List<T> getAll(int limit, long offset);

    T create(T entity);

    T update(T entity);

    boolean delete(K id);

    K count();
}
