package com.sparta.greeypeople.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Getter
@NoArgsConstructor
public class AdminUserResponseDto {
    private String userId;
    private String userName;
    private String intro;

    public AdminUserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUsername();
        this.intro = user.getIntro();
    }
}
