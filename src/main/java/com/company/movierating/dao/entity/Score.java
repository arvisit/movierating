package com.company.movierating.dao.entity;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class Score {
    private Long id;
    private Film film;
    private User user;
    private Integer value;
    private ZonedDateTime publicationDate;
}
