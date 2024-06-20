package com.sparta.greeypeople.user.service;

import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.user.dto.request.AdminUserAuthRequestDto;
import com.sparta.greeypeople.user.dto.request.AdminUserProfileRequestDto;
import com.sparta.greeypeople.user.dto.response.AdminUserResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
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
        User user = findUser(userId);

        user.updateProfile(requestDto);

        return new AdminUserResponseDto(user);
    }

    public void deleteUser(Long userId) {
        User user = findUser(userId);

        userRepository.delete(user);
    }

    public void updateUserAuth(Long userId, AdminUserAuthRequestDto requestDto) {
        User user = findUser(userId);

        user.updateAuth(requestDto);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("해당 사용자는 존재하지 않습니다.")
        );
    }
}
