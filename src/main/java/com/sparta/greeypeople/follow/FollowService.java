package com.sparta.greeypeople.follow;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ViolatedException;
import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import java.util.ArrayList;
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

    @Transactional
    public void followStore(Long storeId, User user) {
        User foundUser = userRepository.findById(user.getId()).orElseThrow(() ->
            new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
        Store foundStore = storeRepository.findById(storeId).orElseThrow(() ->
            new DataNotFoundException("해당 스토어를 찾을 수 없습니다."));

        if (followRepository.findByUserAndStore(user, foundStore).isPresent()) {
            throw new ViolatedException("이미 팔로우한 스토어입니다.");
        }

        followRepository.save(new Follow(foundUser, foundStore));
    }

    @Transactional
    public void unfollowStore(Long storeId, User user) {
        User foundUser = userRepository.findById(user.getId()).orElseThrow(() ->
            new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
        Store foundStore = storeRepository.findById(storeId).orElseThrow(() ->
            new DataNotFoundException("해당 스토어가 존재하지 않습니다."));

        Follow follow = followRepository.findByUserAndStore(foundUser, foundStore).orElseThrow(() ->
            new DataNotFoundException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);
    }

    @Transactional(readOnly = true)
    public List<Menu> getMenusFromFollowedStores(User user) {
        // 팔로우한 가게들의 목록 가져오기
        List<Store> followedStores = followRepository.findFollowedStoresByUser(user);

        // 각 가게별 메뉴 조회 및 최신순 정렬
        List<Menu> menus = new ArrayList<>();
        for (Store store : followedStores) {
            List<Menu> storeMenus = storeRepository.findMenusByStoreIdOrderByCreatedAtDesc(store.getId());
            menus.addAll(storeMenus);
        }

        return menus;
    }

}