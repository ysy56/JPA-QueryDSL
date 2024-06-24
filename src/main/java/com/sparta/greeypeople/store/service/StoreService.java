package com.sparta.greeypeople.store.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.store.dto.response.AdminStoreResponseDto;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public AdminStoreResponseDto getStore(Long storeId) {
        Store store = findStore(storeId);

        return new AdminStoreResponseDto(store);
    }

    public List<AdminStoreResponseDto> getAllStore() {
        return storeRepository.findAll().stream().map(AdminStoreResponseDto::new).toList();
    }

    public Store findStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("조회된 가게의 정보가 없습니다.")
        );
    }
}
