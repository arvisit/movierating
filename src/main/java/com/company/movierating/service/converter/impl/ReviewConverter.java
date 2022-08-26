package com.company.movierating.service.converter.impl;

import com.company.movierating.dao.entity.Review;
import com.company.movierating.service.converter.Converter;
import com.company.movierating.service.dto.ReviewDto;

public class ReviewConverter implements Converter<Review, ReviewDto> {
    private final FilmConverter filmConverter;
    private final UserConverter userConverter;

    public ReviewConverter(FilmConverter filmConverter, UserConverter userConverter) {
        this.filmConverter = filmConverter;
        this.userConverter = userConverter;
    }

    @Override
    public ReviewDto toDto(Review entity) {
        ReviewDto dto = new ReviewDto();
        dto.setId(entity.getId());
        dto.setFilm(filmConverter.toDto(entity.getFilm()));
        dto.setUser(userConverter.toDto(entity.getUser()));
        dto.setContent(entity.getContent());
        dto.setPublicationDate(entity.getPublicationDate());

        return dto;
    }

    @Override
    public Review toEntity(ReviewDto dto) {
        Review entity = new Review();
        entity.setId(dto.getId());
        entity.setFilm(filmConverter.toEntity(dto.getFilm()));
        entity.setUser(userConverter.toEntity(dto.getUser()));
        entity.setContent(dto.getContent());
        entity.setPublicationDate(dto.getPublicationDate());

        return entity;
    }

}
