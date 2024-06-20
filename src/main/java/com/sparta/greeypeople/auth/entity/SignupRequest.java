package com.sparta.greeypeople.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String userId;
    private String password;
    private String userName;
    private String intro;
    private Boolean admin;
    private String adminToken;
}
