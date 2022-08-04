package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.entity.User;
import com.company.movierating.exception.controller.NonAuthorizedException;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.exception.service.UpdateWrongRecordAttemptException;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;
import com.company.movierating.service.validation.UserValidator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto getById(Long id) {
        log.debug("User service method _getById_ was called");
        User entity = userDao.getById(id);
        if (entity == null) {
            throw new NoRecordFoundException("There is no user with id= " + id);
        }
        return toDto(entity);
    }

    @Override
    public UserDto getByEmail(String email) {
        log.debug("User service method get by email was called");
        User entity = userDao.getByEmail(email);
        if (entity == null) {
            throw new NoRecordFoundException("There is no user with email= " + email);
        }
        return toDto(entity);
    }

    @Override
    public UserDto getByLogin(String login) {
        log.debug("User service method get by email was called");
        User entity = userDao.getByLogin(login);
        if (entity == null) {
            throw new NoRecordFoundException("There is no user with login= " + login);
        }
        return toDto(entity);
    }

    @Override
    public List<UserDto> getAll() {
        log.debug("User service method _getAll_ was called");
        return userDao.getAll().stream() //
                .map(this::toDto) //
                .toList();
    }

    @Override
    public UserDto create(UserDto entity) {
        log.debug("User service method _create_ was called");
        UserValidator.validateUserToCreate(entity);
        entity.setRole(UserDto.Role.USER);
        User createdEntity = userDao.create(toEntity(entity));
        return toDto(createdEntity);
    }

    @Override
    public UserDto update(UserDto entity) {
        log.debug("User service method _update_ was called");
        User existing = userDao.getByEmail(entity.getEmail());
        if (existing != null && existing.getId() != entity.getId()) {
            throw new UpdateWrongRecordAttemptException("User with email= " + entity.getEmail() + " already exists");
        }
        existing = userDao.getByLogin(entity.getLogin());
        if (existing != null && existing.getId() != entity.getId()) {
            throw new UpdateWrongRecordAttemptException("User with login= " + entity.getLogin() + " already exists");
        }
        User createdEntity = userDao.update(toEntity(entity));
        return toDto(createdEntity);
    }

    @Override
    public boolean delete(Long id) {
        log.debug("User service method _delete_ was called");
        return userDao.delete(id);
    }

    @Override
    public UserDto approveSubject(UserDto actor, UserDto subject) {
        if (actor.getRole() != UserDto.Role.ADMIN) {
            if (actor.getId() != subject.getId()) {
                throw new ForbiddenPageException("You have not enough rigths to view this page");
            }
        }
        return subject;
    }

    @Override
    public UserDto signIn(String login, String password) {
        User user = userDao.getByLogin(login);
        if (user == null) {
            throw new NonAuthorizedException("No such login found");
        }
        if (!user.getPassword().equals(password)) {
            throw new NonAuthorizedException("Wrong password");
        }
        return toDto(user);
    }

    private UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setRole(UserDto.Role.valueOf(entity.getRole().toString()));
        dto.setRegistration(entity.getRegistration());
        dto.setInfo(entity.getInfo());
        dto.setReputation(entity.getReputation());
        return dto;
    }

    private User toEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        entity.setRole(User.Role.valueOf(dto.getRole().toString()));
        entity.setRegistration(dto.getRegistration());
        entity.setInfo(dto.getInfo());
        entity.setReputation(dto.getReputation());
        return entity;
    }

}
