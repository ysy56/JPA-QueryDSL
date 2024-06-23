package com.sparta.greeypeople.store.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.store.dto.request.AdminStoreSaveRequestDto;
import com.sparta.greeypeople.store.dto.request.AdminStoreUpdateRequestDto;
import com.sparta.greeypeople.store.dto.response.AdminStoreResponseDto;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public AdminStoreResponseDto updateStore(Long storeId, AdminStoreUpdateRequestDto requestDto) {
        Store store = findStore(storeId);

        store.update(requestDto);

        return new AdminStoreResponseDto(store);
    }

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
