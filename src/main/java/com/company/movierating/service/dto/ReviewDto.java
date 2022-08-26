package com.company.movierating.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private FilmDto film;
    private UserDto user;
    private String content;
    private ZonedDateTime publicationDate;
}
