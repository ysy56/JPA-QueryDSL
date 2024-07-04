package com.sparta.greeypeople.test;

import com.sparta.greeypeople.review.dto.request.ReviewRequestDto;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.store.dto.request.AdminStoreSaveRequestDto;
import com.sparta.greeypeople.store.entity.Store;

public interface StoreTest {
    String TEST_STORE_NAME = "testStoreName";
    String TEST_STORE_INTRO = "This is a test store introduction";

    AdminStoreSaveRequestDto TEST_ADMIN_STORE_SAVE_REQUEST_DTO = new AdminStoreSaveRequestDto(
            TEST_STORE_NAME,
            TEST_STORE_INTRO
    );

    Store TEST_STORE = new Store(TEST_ADMIN_STORE_SAVE_REQUEST_DTO);

}
