package com.company.movierating.dao.entity;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class Ban {
    private Long id;
    private Long userId;
    private Long adminId;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String reason;
}
