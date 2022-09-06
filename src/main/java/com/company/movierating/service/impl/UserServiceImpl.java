package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.entity.User;
import com.company.movierating.exception.controller.NonAuthorizedException;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.service.UserService;
import com.company.movierating.service.converter.impl.UserConverter;
import com.company.movierating.service.dto.UserDto;
import com.company.movierating.service.util.DigestUtil;
import com.company.movierating.service.util.UserValidator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserConverter userConverter;
    private final UserValidator userValidator;

    public UserServiceImpl(UserDao userDao, UserConverter userConverter, UserValidator userValidator) {
        this.userDao = userDao;
        this.userConverter = userConverter;
        this.userValidator = userValidator;
    }

    @Override
    public UserDto getById(Long id) {
        log.debug("User service method _getById_ was called");
        User entity = userDao.getById(id);
        if (entity == null) {
            throw new NoRecordFoundException("There is no user with id= " + id);
        }
        entity.setPassword(null);
        return userConverter.toDto(entity);
    }

    @Override
    public UserDto getByEmail(String email) {
        log.debug("User service method get by email was called");
        User entity = userDao.getByEmail(email);
        if (entity == null) {
            throw new NoRecordFoundException("There is no user with email= " + email);
        }
        entity.setPassword(null);
        return userConverter.toDto(entity);
    }

    @Override
    public UserDto getByLogin(String login) {
        log.debug("User service method get by email was called");
        User entity = userDao.getByLogin(login);
        if (entity == null) {
            throw new NoRecordFoundException("There is no user with login= " + login);
        }
        entity.setPassword(null);
        return userConverter.toDto(entity);
    }

    @Override
    public List<UserDto> getAll() {
        log.debug("User service method _getAll_ was called");
        return userDao.getAll().stream() //
                .map(dao -> {
                    dao.setPassword(null);
                    return userConverter.toDto(dao);
                }) //
                .toList();
    }

    @Override
    public List<UserDto> getAll(int limit, long offset) {
        log.debug("User service method _getAll_ (paged) was called");
        return userDao.getAll(limit, offset).stream() //
                .map(dao -> {
                    dao.setPassword(null);
                    return userConverter.toDto(dao);
                }) //
                .toList();
    }

    @Override
    public UserDto create(UserDto dto) {
        log.debug("User service method _create_ was called");
        userValidator.validateUserToCreate(dto);
        dto.setRole(UserDto.Role.USER);
        dto.setPassword(DigestUtil.INSTANCE.hash(dto.getPassword()));
        User createdEntity = userDao.create(userConverter.toEntity(dto));
        createdEntity.setPassword(null);
        return userConverter.toDto(createdEntity);
    }

    @Override
    public UserDto create(UserDto dto, String confirmedPassword) {
        log.debug("User service method _create_ was called");
        userValidator.validateUserToCreate(dto, confirmedPassword);
        dto.setRole(UserDto.Role.USER);
        dto.setPassword(DigestUtil.INSTANCE.hash(dto.getPassword()));
        User createdEntity = userDao.create(userConverter.toEntity(dto));
        createdEntity.setPassword(null);
        return userConverter.toDto(createdEntity);
    }

    @Override
    public UserDto update(UserDto dto) {
        log.debug("User service method _update_ was called");
        UserDto dtoToUpdate = userConverter.toDto(userDao.getById(dto.getId()));

        dtoToUpdate.setEmail(dto.getEmail());
        dtoToUpdate.setInfo(dto.getInfo());
        dtoToUpdate.setReputation(dto.getReputation());
        dtoToUpdate.setRole(dto.getRole());
        dtoToUpdate.setAvatar(dto.getAvatar());

        userValidator.validateUserToUpdate(dtoToUpdate);
        User updatedEntity = userDao.update(userConverter.toEntity(dtoToUpdate));
        updatedEntity.setPassword(null);
        return userConverter.toDto(updatedEntity);
    }

    @Override
    public boolean delete(Long id) {
        log.debug("User service method _delete_ was called");
        return userDao.delete(id);
    }

    @Override
    public Long count() {
        log.debug("User service method _count_ was called");
        return userDao.count();
    }

    @Override
    public UserDto signIn(String login, String password) {
        User user = userDao.getByLogin(login);
        if (user == null) {
            throw new NonAuthorizedException("No such login found");
        }
        String hashedPassword = DigestUtil.INSTANCE.hash(password);
        if (!user.getPassword().equals(hashedPassword)) {
            throw new NonAuthorizedException("Wrong password");
        }
        user.setPassword(null);
        return userConverter.toDto(user);
    }

}
