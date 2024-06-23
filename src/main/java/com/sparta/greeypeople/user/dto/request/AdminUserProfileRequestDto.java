package com.sparta.greeypeople.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUserProfileRequestDto {

    @NotBlank(message = "사용자 이름을 입력해주세요")
    private String userName;

    private String intro;

}
