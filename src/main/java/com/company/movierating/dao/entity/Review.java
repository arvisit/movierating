package com.company.movierating.dao.entity;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class Review {
    private Long id;
    private Film film;
    private User user;
    private String content;
    private ZonedDateTime publicationDate;
}
