package com.company.movierating.dao.entity;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String login;
    private String email;
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
