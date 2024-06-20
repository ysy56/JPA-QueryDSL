package com.sparta.greeypeople.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class AdminUserProfileRequestDto {

    @NotBlank(message = "사용자 이름을 입력해주세요")
    private String userName;

    private String intro;
}
