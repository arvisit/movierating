package com.company.movierating.service.dto;

import lombok.Data;

@Data
public class FilmDto {
    private Long id;
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer length;
    private AgeRating ageRating;

    public enum AgeRating {
        G, PG, PG_13, R, NC_17;
    }
}
