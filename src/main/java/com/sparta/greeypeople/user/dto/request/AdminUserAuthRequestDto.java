package com.sparta.greeypeople.user.dto.request;

import com.sparta.greeypeople.user.enumeration.UserAuth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUserAuthRequestDto {

    @NotBlank(message = "변경할 권한을 입력해주세요")
    @Pattern(regexp = "^(ADMIN|USER)$\n", message = "(USER, ADMIN) 대문자로만 입력해주세요.")
    private UserAuth userAuth;

}
