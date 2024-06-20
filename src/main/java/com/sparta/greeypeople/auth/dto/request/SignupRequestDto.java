package com.sparta.greeypeople.auth.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * 회원가입 요청 DTO
 * 사용자 회원가입을 위한 데이터 전송 객체
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDto {

    @NotBlank(message = "사용자 ID를 입력해주세요")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "사용자 ID는 알파벳 소문자와 숫자로 이루어진 4자에서 10자 사이여야 합니다.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,15}$", message = "비밀번호는 대소문자 영문, 숫자, 특수문자를 최소 1글자씩 포함하며 최소 8자에서 15자 사이여야 합니다.")
    private String password;

    @NotBlank(message = "사용자 이름을 입력해주세요")
    private String userName;

    private String intro;

    @Builder.Default
    private Boolean admin = false;
    private String adminToken;
}
