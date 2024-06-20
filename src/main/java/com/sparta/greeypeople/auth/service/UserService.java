package com.sparta.greeypeople.auth.service;

import com.sparta.greeypeople.auth.dto.SignupRequestDto;
import com.sparta.greeypeople.auth.dto.TokenResponseDto;
import com.sparta.greeypeople.auth.entity.LoginRequest;

public interface UserService {
    void signup(SignupRequestDto signupRequest);

    TokenResponseDto login(LoginRequest loginRequest);
    void logout(String userId);
    void withdraw(String userId, String password);
    TokenResponseDto refresh(String refreshToken);
}
