package com.sparta.greeypeople.follow.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.follow.Follow;
import com.sparta.greeypeople.follow.FollowRepository;
import com.sparta.greeypeople.follow.dto.AdminFollowResponseDto;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    public List<AdminFollowResponseDto> findStoreAllFollower(Long storeId) {
        List<Follow> followerList = findFollowerList(storeId);
        List<AdminFollowResponseDto> responseList = new ArrayList<>();

        for (Follow follow : followerList) {
            User user = follow.getUser();
            Long userId = user.getId();
            User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("해당 사용자는 존재하지 않습니다."));

            AdminFollowResponseDto responseDto = new AdminFollowResponseDto(findUser.getUserName());
            responseList.add(responseDto);
        }

        return responseList;
    }

    public List<Follow> findFollowerList(Long storeId) {
        return followRepository.findAllByStoreId(storeId).stream().toList();
    }

}
