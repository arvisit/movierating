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
    private boolean banned;

    public enum Role {
        ADMIN, USER
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", login=" + login + ", email=" + email + ", role=" + role + ", info=" + info
                + ", reputation=" + reputation + ", registration=" + registration + "]";
    }
}
