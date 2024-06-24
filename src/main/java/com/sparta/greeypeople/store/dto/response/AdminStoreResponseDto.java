package com.sparta.greeypeople.store.dto.response;

import com.sparta.greeypeople.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminStoreResponseDto {

    private String storeName;
    private String intro;

    public AdminStoreResponseDto(Store store) {
        this.storeName = store.getStoreName();
        this.intro = store.getIntro();
    }
}
