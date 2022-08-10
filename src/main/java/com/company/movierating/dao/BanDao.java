package com.company.movierating.dao;

import java.util.List;

import com.company.movierating.dao.entity.Ban;

public interface BanDao extends AbstractDao<Long, Ban> {
    List<Ban> getAllByUser(Long id, int limit, long offset);

    List<Ban> getAllByAdmin(Long id, int limit, long offset);

    boolean isBanned(Long userId);
}
