package com.sparta.greeypeople.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 변경 요청 DTO
 */
@Getter
@NoArgsConstructor
public class PasswordUpdateRequestDto {

    @NotBlank(message = "기존 패스워드를 입력해주세요.")
    private String password;

    @NotBlank(message = "새로운 패스워드를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,15}$",
        message = "비밀번호는 대소문자 영문, 숫자, 특수문자를 최소 1글자씩 포함하며 최소 8자에서 15자 사이여야 합니다.")
    private String newPassword;
}
