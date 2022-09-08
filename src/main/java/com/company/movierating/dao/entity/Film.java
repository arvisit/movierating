package com.company.movierating.dao.entity;

import lombok.Data;

@Data
public class Film {
    private Long id;
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer length;
    private AgeRating ageRating;
    private String poster;

    public enum AgeRating {
        G, PG, PG_13, R, NC_17;
    }
}
