package com.sparta.greeypeople.user.service;

import com.sparta.greeypeople.exception.ConflictException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.user.dto.request.AdminUserAuthRequestDto;
import com.sparta.greeypeople.user.dto.request.AdminUserProfileRequestDto;
import com.sparta.greeypeople.user.dto.response.AdminUserResponseDto;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import com.sparta.greeypeople.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

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

    @Transactional
    public void updateUserAuth(Long userId, AdminUserAuthRequestDto requestDto) {
        User user = findUser(userId);

        if (user.getUserAuth().equals(requestDto.getUserAuth())) {
            throw new ConflictException("해당 사용자의 변경하려고 하는 권한과 현재의 권한이 같습니다.");
        }

        UserAuth userAuth = requestDto.getUserAuth();

        user.updateAuth(userAuth);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("해당 사용자는 존재하지 않습니다.")
        );
    }
}
