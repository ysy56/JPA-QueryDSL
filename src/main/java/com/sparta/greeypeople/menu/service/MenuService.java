package com.sparta.greeypeople.menu.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.menu.dto.response.AdminMenuResponseDto;
import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.menu.repository.MenuRepository;
import com.sparta.greeypeople.store.repository.StoreRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public MenuService(StoreRepository storeRepository, MenuRepository menuRepository){
        this.storeRepository = storeRepository;
        this.menuRepository = menuRepository;
    }

    public List<AdminMenuResponseDto> getStoreMenu(Long storeId) {
        storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("해당 주문은 존재하지 않습니다")
        );
        List<Menu> menus = menuRepository.findByStoreId(storeId);
        return menus.stream().map(AdminMenuResponseDto::new).collect(Collectors.toList());
    }
}
