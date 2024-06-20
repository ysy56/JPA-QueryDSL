package com.sparta.greeypeople.auth.service;

import com.sparta.greeypeople.auth.dto.SignupRequestDto;
import com.sparta.greeypeople.auth.entity.LoginRequest;
import com.sparta.greeypeople.auth.dto.TokenResponseDto;
import com.sparta.greeypeople.auth.entity.SignupRequest;

public interface UserService {
    void signup(SignupRequest signupRequest);

    void signup(SignupRequestDto signupRequest);

    TokenResponseDto login(LoginRequest loginRequest);
    void logout(String userId);
    void withdraw(String userId, String password);
    TokenResponseDto refresh(String refreshToken);
}
