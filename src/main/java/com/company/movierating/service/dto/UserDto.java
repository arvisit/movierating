package com.company.movierating.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String login;
    private String email;
    private String password;
    private Role role;
    private String info;
    private Integer reputation;
    private ZonedDateTime registration;

    public enum Role {
        ADMIN, USER
    }
}
