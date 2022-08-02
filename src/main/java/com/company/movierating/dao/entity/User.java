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
    private ZonedDateTime registration;

    public enum Role {
        ADMIN, USER
    }

    public void setEmail(String email) {
        if (!email.matches("[\\w\\.]+@[\\w\\.]+\\.\\w+")) {
            throw new IllegalArgumentException(email + " is not valid value for email");
        }
        this.email = email;
    }
}
