package com.sparta.greeypeople.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUserAuthRequestDto {

    @NotBlank(message = "변경할 권한을 입력해주세요")
    private String userAuth;

}
