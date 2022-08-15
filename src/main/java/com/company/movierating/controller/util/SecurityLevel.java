package com.company.movierating.controller.util;

import lombok.Getter;

public enum SecurityLevel {
    GUEST(0), //
    USER(1), //
    USER_SELF(2), //
    ADMIN(3), //
    ADMIN_SELF(4);

    @Getter
    private final int value;

    SecurityLevel(int value) {
        this.value = value;
    }
}
