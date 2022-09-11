package com.company.movierating.service.dto;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.ToString;

@Data
public class UserDto {
    private Long id;
    private String login;
    private String email;
    @ToString.Exclude
    private String password;
    private Role role;
    private String info;
    private Integer reputation;
    private String avatar;
    private ZonedDateTime registration;

    public enum Role {
        ADMIN, USER
    }
}
