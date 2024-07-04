package com.sparta.greeypeople.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminStoreSaveRequestDto {

    @NotBlank(message = "가게 이름을 입력해주세요")
    private String storeName;

    private String intro;

}
