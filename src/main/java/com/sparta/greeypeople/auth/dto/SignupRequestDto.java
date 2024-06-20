package com.sparta.greeypeople.auth.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "사용자 ID를 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]{10,20}$", message = "사용자 ID는 알파벳 대소문자와 숫자로 이루어진 10자에서 20자 사이여야 합니다.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{10,}$", message = "비밀번호는 대소문자 영문, 숫자, 특수문자를 최소 1글자씩 포함하며 최소 10자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "사용자 이름을 입력해주세요")
    private String userName;

    private String intro;

    private Boolean admin;
    private String adminToken;
}
