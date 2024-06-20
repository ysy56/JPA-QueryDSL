package com.sparta.greeypeople.store.service;

import com.sparta.greeypeople.store.dto.request.AdminStoreSaveRequestDto;
import com.sparta.greeypeople.store.dto.response.AdminStoreResponseDto;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminStoreService {

    private final StoreRepository storeRepository;

    public AdminStoreResponseDto saveStore(AdminStoreSaveRequestDto requestDto) {
        Store store = storeRepository.save(new Store(requestDto));

        return new AdminStoreResponseDto(store);
    }
}
