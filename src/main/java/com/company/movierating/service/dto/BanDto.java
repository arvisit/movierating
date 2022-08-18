package com.company.movierating.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class BanDto {
    private Long id;
    private UserDto user;
    private UserDto admin;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String reason;
}
