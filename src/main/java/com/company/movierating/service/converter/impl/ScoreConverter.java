package com.company.movierating.service.converter.impl;

import com.company.movierating.dao.entity.Score;
import com.company.movierating.service.converter.Converter;
import com.company.movierating.service.dto.ScoreDto;

public class ScoreConverter implements Converter<Score, ScoreDto> {
    private final FilmConverter filmConverter;
    private final UserConverter userConverter;

    public ScoreConverter(FilmConverter filmConverter, UserConverter userConverter) {
        this.filmConverter = filmConverter;
        this.userConverter = userConverter;
    }

    @Override
    public ScoreDto toDto(Score entity) {
        ScoreDto dto = new ScoreDto();
        dto.setId(entity.getId());
        dto.setFilm(filmConverter.toDto(entity.getFilm()));
        dto.setUser(userConverter.toDto(entity.getUser()));
        dto.setValue(entity.getValue());
        dto.setPublicationDate(entity.getPublicationDate());

        return dto;
    }

    @Override
    public Score toEntity(ScoreDto dto) {
        Score entity = new Score();
        entity.setId(dto.getId());
        entity.setFilm(filmConverter.toEntity(dto.getFilm()));
        entity.setUser(userConverter.toEntity(dto.getUser()));
        entity.setValue(dto.getValue());
        entity.setPublicationDate(dto.getPublicationDate());

        return entity;
    }

}
