package com.company.movierating.controller.util;

import lombok.Getter;

public enum SecurityLevel {
    GUEST(0), //
    USER(1), //
    USER_SELF(2), //
    USER_SELF_NOT_BANNED(3), //
    ADMIN(4), //
    ADMIN_SELF(5);

    @Getter
    private final int value;

    SecurityLevel(int value) {
        this.value = value;
    }
}
