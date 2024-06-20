package com.sparta.greeypeople.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 요청 DTO
 * 회원탈퇴 시 사용
 */
@Getter
@NoArgsConstructor
public class PasswordRequestDto {

    /**
     * 비밀번호
     */
    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
}
