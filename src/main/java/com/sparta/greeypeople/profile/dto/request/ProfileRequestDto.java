package com.sparta.greeypeople.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로필 수정 요청 DTO
 */
@Getter
@NoArgsConstructor
public class ProfileRequestDto {

    @NotBlank(message = "사용자 이름을 입력해주세요")
    private String userName;

    @NotBlank(message = "한 줄 소개를 입력해주세요")
    private String intro;
}
