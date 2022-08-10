package com.company.movierating.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class BanDto {
    private Long id;
    private Long userId;
    private Long adminId;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String reason;
}
