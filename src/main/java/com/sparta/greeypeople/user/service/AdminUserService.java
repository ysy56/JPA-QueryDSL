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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    private final UserRepository userRepository;

    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<AdminUserResponseDto> getAllUsers(Long adminUserId) {
        checkAuth(adminUserId);

        return userRepository.findAll()
                .stream().map(AdminUserResponseDto::new).toList();
    }

    @Transactional
    public AdminUserResponseDto updateUserProfile(Long userId, AdminUserProfileRequestDto requestDto, Long adminUserId) {
        checkAuth(adminUserId);

        User user = findUser(userId);

        user.updateProfile(requestDto);

        return new AdminUserResponseDto(user);
    }

    public void deleteUser(Long userId, Long adminUserId) {
        checkAuth(adminUserId);

        User user = findUser(userId);

        userRepository.delete(user);
    }

    public void updateUserAuth(Long userId, AdminUserAuthRequestDto requestDto, Long adminUserId) {
        checkAuth(adminUserId);

        User user = findUser(userId);

        UserAuth userAuth;

        if (requestDto.getUserAuth().equals("admin")) {
            userAuth = UserAuth.ADMIN;
        } else {
            userAuth = UserAuth.USER;
        }

        if (user.getUserAuth().equals(userAuth)) {
            throw new ConflictException("해당 사용자의 변경하려고 하는 권한과 현재의 권한이 같습니다.");
        }

        user.updateAuth(userAuth);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("해당 사용자는 존재하지 않습니다.")
        );
    }

    public void checkAuth(Long userId) {
        User user = findUser(userId);

        UserAuth userAuth = UserAuth.ADMIN;

        if (user.getUserAuth() != userAuth) {
            throw new ForbiddenException("해당 사용자는 관리자가 아닙니다.");
        }
    }
}
