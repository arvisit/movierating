package com.company.movierating.service.converter.impl;

import com.company.movierating.dao.entity.Ban;
import com.company.movierating.service.converter.Converter;
import com.company.movierating.service.dto.BanDto;

public class BanConverter implements Converter<Ban, BanDto> {
    private final UserConverter userConverter;

    public BanConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public BanDto toDto(Ban entity) {
        BanDto dto = new BanDto();
        dto.setId(entity.getId());
        dto.setUser(userConverter.toDto(entity.getUser()));
        dto.setAdmin(userConverter.toDto(entity.getAdmin()));
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setReason(entity.getReason());
        return dto;
    }

    @Override
    public Ban toEntity(BanDto dto) {
        Ban entity = new Ban();
        entity.setId(dto.getId());
        entity.setUser(userConverter.toEntity(dto.getUser()));
        entity.setAdmin(userConverter.toEntity(dto.getAdmin()));
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReason(dto.getReason());
        return entity;
    }

}
