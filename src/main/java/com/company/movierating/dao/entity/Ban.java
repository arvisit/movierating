package com.company.movierating.dao.entity;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class Ban {
    private Long id;
    private User user;
    private User admin;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String reason;
}
