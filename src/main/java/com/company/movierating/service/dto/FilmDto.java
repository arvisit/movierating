package com.company.movierating.service.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class FilmDto {
    private Long id;
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer length;
    private AgeRating ageRating;
    private Double averageScore;

    public enum AgeRating {
        G("G"), PG("PG"), PG_13("PG-13"), R("R"), NC_17("NC-17");

        @Getter
        private final String name;

        AgeRating(String name) {
            this.name = name;
        }
    }
}
