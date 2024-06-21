package com.sparta.greeypeople.follow.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.follow.dto.AdminFollowResponseDto;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    public List<AdminFollowResponseDto> selectStoreAllFollower(Long storeId) {
        List<Follow> followerList = findFollowerList(storeId);
        List<AdminFollowResponseDto> responseList = new ArrayList<>();

        for (Follow follow : followerList) {
            Long userId = follow.getUserId();
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("해당 사용자는 존재하지 않습니다."));

            AdminFollowResponseDto responseDto = new AdminFollowResponseDto(user.getUsername());
            responseList.add(responseDto);
        }

        return responseList;
    }

    public List<Follow> findFollowerList(Long storeId) {
        return followRepository.findByStoreId().orElseThrow(
                () -> new DataNotFoundException("조회된 팔로워의 정보가 없습니다.")
            )
            .stream().map(BoardResponseDto::new).toList();
    }
}
