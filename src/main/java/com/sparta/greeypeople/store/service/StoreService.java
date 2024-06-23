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

    //가게 단건 조회
    public AdminStoreResponseDto getStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("해당 가게는 존재하지 않습니다")
        );
        return new AdminStoreResponseDto(store);
    }


    //가게 전체 조회
    public List<AdminStoreResponseDto> getAllStore() {
        return storeRepository.findAll().stream().map(AdminStoreResponseDto::new).toList();
    }
}
