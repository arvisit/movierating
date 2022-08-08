package com.company.movierating.service.impl;

import java.util.List;

import com.company.movierating.dao.UserDao;
import com.company.movierating.dao.entity.User;
import com.company.movierating.exception.controller.NonAuthorizedException;
import com.company.movierating.exception.service.ForbiddenPageException;
import com.company.movierating.exception.service.NoRecordFoundException;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;
import com.company.movierating.service.util.DigestUtil;
import com.company.movierating.service.util.UserValidator;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserValidator validator;

    public UserServiceImpl(UserDao userDao, UserValidator validator) {
        this.userDao = userDao;
        this.validator = validator;
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
    public UserDto getById(UserDto actor, Long id) {
        log.debug("User service method _getById_ was called");
        User entity = userDao.getById(id);
        if (entity == null) {
            throw new NoRecordFoundException("There is no user with id= " + id);
        }
        return approveSubject(actor, toDto(entity));
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
    public List<UserDto> getAll(int limit, long offset) {
        log.debug("User service method _getAll_ (paged) was called");
        return userDao.getAll(limit, offset).stream() //
                .map(this::toDto) //
                .toList();
    }

    @Override
    public UserDto create(UserDto dto) {
        log.debug("User service method _create_ was called");
        validator.validateUserToCreate(dto);
        dto.setRole(UserDto.Role.USER);
        dto.setPassword(DigestUtil.INSTANCE.hash(dto.getPassword()));
        User createdEntity = userDao.create(toEntity(dto));
        return toDto(createdEntity);
    }

    @Override
    public UserDto create(UserDto dto, String confirmedPassword) {
        log.debug("User service method _create_ was called");
        validator.validateUserToCreate(dto, confirmedPassword);
        dto.setRole(UserDto.Role.USER);
        dto.setPassword(DigestUtil.INSTANCE.hash(dto.getPassword()));
        User createdEntity = userDao.create(toEntity(dto));
        return toDto(createdEntity);
    }

    @Override
    public UserDto update(UserDto dto) {
        log.debug("User service method _update_ was called");
        validator.validateUserToUpdate(dto);
        User createdEntity = userDao.update(toEntity(dto));
        return toDto(createdEntity);
    }

    @Override
    public boolean delete(Long id) {
        log.debug("User service method _delete_ was called");
        return userDao.delete(id);
    }

    @Override
    public Long count() {
        log.debug("User service method count_ was called");
        return userDao.count();
    }

    @Override
    public UserDto approveSubject(UserDto actor, UserDto subject) {
        if (actor.getRole() != UserDto.Role.ADMIN) {
            if (actor.getId() != subject.getId()) {
                throw new ForbiddenPageException("You have not enough rigths");
            }
        } else {
            if (subject.getRole() == UserDto.Role.ADMIN && actor.getId() != subject.getId()) {
                throw new ForbiddenPageException("You have not enough rigths");
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
        String hashedPassword = DigestUtil.INSTANCE.hash(password);
        if (!user.getPassword().equals(hashedPassword)) {
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
