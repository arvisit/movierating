package com.company.movierating.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class ScoreDto {
    private Long id;
    private FilmDto film;
    private UserDto user;
    private Integer value;
    private ZonedDateTime publicationDate;
}
