package com.sparta.greeypeople.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.greeypeople.user.entity.User;

@Getter
@NoArgsConstructor
public class AdminUserResponseDto {
    private String userId;
    private String userName;
    private String intro;

    public AdminUserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.intro = user.getIntro();
    }

}
