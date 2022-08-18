package com.company.movierating.service.converter.impl;

import com.company.movierating.dao.entity.User;
import com.company.movierating.service.converter.Converter;
import com.company.movierating.service.dto.UserDto;

public class UserConverter implements Converter<User, UserDto> {

    @Override
    public UserDto toDto(User entity) {
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

    @Override
    public User toEntity(UserDto dto) {
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
