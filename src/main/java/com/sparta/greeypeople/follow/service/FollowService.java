package com.sparta.greeypeople.follow.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ViolatedException;
import com.sparta.greeypeople.follow.entity.Follow;
import com.sparta.greeypeople.follow.repository.FollowRepository;
import com.sparta.greeypeople.menu.dto.response.MenuResponseDto;
import com.sparta.greeypeople.menu.repository.MenuRepository;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void followStore(Long storeId, User user) {
        User foundUser = validateUser(user);
        Store foundStore = validateStore(storeId);

        if (followRepository.findByUserAndStore(foundUser, foundStore).isPresent()) {
            throw new ViolatedException("이미 팔로우한 스토어입니다.");
        }

        followRepository.save(new Follow(foundUser, foundStore));
    }

    @Transactional
    public void unfollowStore(Long storeId, User user) {
        User foundUser = validateUser(user);
        Store foundStore = validateStore(storeId);

        Follow follow = followRepository.findByUserAndStore(foundUser, foundStore).orElseThrow(() ->
            new DataNotFoundException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> getMenusFromFollowedStores(User user) {
        User validatedUser = validateUser(user);

        // 팔로우한 가게들의 ID 목록 가져오기
        List<Long> followedStoreIds = followRepository.findFollowedStoreIdsByUser(validatedUser);

        // 팔로우한 모든 가게의 메뉴를 최신순으로 정렬하여 가져오기
        List<MenuResponseDto> menus = menuRepository.findMenusFromFollowedStoresOrderByCreatedAtDesc(followedStoreIds);

        return menus;
    }

    private User validateUser(User user) {
        return userRepository.findById(user.getId()).orElseThrow(() ->
            new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
    }

    private Store validateStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(() ->
            new DataNotFoundException("해당 스토어가 존재하지 않습니다."));
    }
}
