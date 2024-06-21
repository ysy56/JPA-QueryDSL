package com.sparta.greeypeople.menu.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.menu.dto.request.AdminMenuSaveRequestDto;
import com.sparta.greeypeople.menu.dto.request.AdminMenuUpdateRequestDto;
import com.sparta.greeypeople.menu.dto.response.AdminMenuResponseDto;
import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.menu.repository.MenuRepository;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import com.sparta.greeypeople.user.service.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminMenuService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public AdminMenuResponseDto saveMenu(Long storeId, AdminMenuSaveRequestDto requestDto) {
        User user = findUser();
        Store store = findStore(storeId);

        Menu menu = menuRepository.save(new Menu(requestDto, user, store));

        menuRepository.save(menu);

        return new AdminMenuResponseDto(menu);
    }

    @Transactional
    public AdminMenuResponseDto updateMenu(Long storeId, Long menuId,
        AdminMenuUpdateRequestDto requestDto) {

        Store store = findStore(storeId);
        Menu menu = findMenu(menuId);

        menu.update(requestDto);

        return new AdminMenuResponseDto(menu);
    }

    public void deleteMenu(Long storeId, Long menuId) {
        Store store = findStore(storeId);
        Menu menu = findMenu(menuId);

        menuRepository.delete(menu);
    }

    public User findUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        String userId = userDetails.getUser().getUserId();

        return userRepository.findByUserId(userId).orElseThrow(
            () -> new DataNotFoundException("해당 사용자는 존재하지 않습니다.")
        );
    }

    public Store findStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(
            () -> new DataNotFoundException("해당 가게는 존재하지 않습니다.")
        );
    }

    public Menu findMenu(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(
            () -> new DataNotFoundException("해당 메뉴는 존재하지 않습니다.")
        );
    }

}
