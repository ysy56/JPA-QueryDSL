package com.sparta.greeypeople.user.service;

import com.sparta.greeypeople.user.dto.AdminUserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {

    private final UserRepository userRepository;

    public AdminUserService(UserRepository, userRepository) {
        this.userRepository = userRepository;
    }

    public List<AdminUserResponseDto> sellectAllUser() {
        return userRepository.findAll()
                .stream().map(AdminUserResponseDto::new).toList();
    }
}
