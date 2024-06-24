package com.sparta.greeypeople.follow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminFollowResponseDto {

    private String userName;

    public AdminFollowResponseDto(String userName) {
        this.userName = userName;
    }

}
