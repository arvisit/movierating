package com.company.movierating.dao;

import com.company.movierating.dao.entity.User;

public interface UserDao extends AbstractDao<Long, User> {
    User getByEmail(String email);

    User getByLogin(String login);
}
