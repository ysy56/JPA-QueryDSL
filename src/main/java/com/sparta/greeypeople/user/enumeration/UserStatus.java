package com.sparta.greeypeople.user.enumeration;

public enum UserStatus {
    ACTIVE("ACTIVE"),  //정상
    WITHDRAWN("WITHDRAWN"),  //탈퇴
    BLOCKED("BLOCKED");  //차단

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }
}
