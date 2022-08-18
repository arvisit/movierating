package com.company.movierating.service;

import java.util.List;

public interface AbstractService<K, T> {
    T getById(K id);

    List<T> getAll();

    List<T> getAll(int limit, long offset);

    T create(T dto);

    T update(T dto);

    boolean delete(K id);

    K count();
}
