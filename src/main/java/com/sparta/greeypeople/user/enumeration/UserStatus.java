package com.sparta.greeypeople.user.enumeration;

public enum UserStatus {

    MEMBER("MEMBER"),
    NON_MEMBER("NON_MEMBER");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

}
