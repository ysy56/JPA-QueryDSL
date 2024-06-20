package com.sparta.greeypeople.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUserProfileRequestDto {
    private String userName;
    private String intro;
}
