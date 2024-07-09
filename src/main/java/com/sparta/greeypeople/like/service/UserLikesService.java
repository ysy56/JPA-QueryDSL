package com.sparta.greeypeople.like.service;

import com.sparta.greeypeople.like.repository.MenuLikesRepository;
import com.sparta.greeypeople.menu.dto.response.MenuResponseDto;
import com.sparta.greeypeople.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLikesService {

    private final MenuLikesRepository menuLikesRepository;

    public Page<MenuResponseDto> getLikedMenus(User user, int page, int size) {
        return menuLikesRepository.findLikedMenus(user, PageRequest.of(page, size)).map(MenuResponseDto::new);
    }

}
