package com.sparta.greeypeople.store.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.store.dto.request.AdminStoreSaveRequestDto;
import com.sparta.greeypeople.store.dto.request.AdminStoreUpdateRequestDto;
import com.sparta.greeypeople.store.dto.response.AdminStoreResponseDto;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminStoreService {

    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public AdminStoreResponseDto createStore(AdminStoreSaveRequestDto requestDto) {
        Store store = storeRepository.save(new Store(requestDto));

        return new AdminStoreResponseDto(store);
    }

    @Transactional
    public AdminStoreResponseDto updateStore(Long storeId, AdminStoreUpdateRequestDto requestDto) {
        Store store = findStore(storeId);

        store.update(requestDto);

        return new AdminStoreResponseDto(store);
    }

    @Transactional
    public void deleteStore(Long storeId) {
        Store store = findStore(storeId);

        storeRepository.delete(store);
    }

    public Store findStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("조회된 가게의 정보가 없습니다.")
        );
    }

}
