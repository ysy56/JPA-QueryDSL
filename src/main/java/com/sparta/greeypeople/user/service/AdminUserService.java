package com.sparta.greeypeople.user.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.user.dto.AdminUserProfileRequestDto;
import com.sparta.greeypeople.user.dto.AdminUserResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    private final UserRepository userRepository;

    public AdminUserService(UserRepository, userRepository) {
        this.userRepository = userRepository;
    }

    public List<AdminUserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream().map(AdminUserResponseDto::new).toList();
    }

    @Transactional
    public AdminUserResponseDto updateUserProfile(Long userId, AdminUserProfileRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("해당 사용자는 존재하지 않습니다.")
        );

        user.updateProfile(requestDto);

        return new AdminUserResponseDto(user);
    }
}
