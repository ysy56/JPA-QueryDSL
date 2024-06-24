package com.sparta.greeypeople.profile.service;

import com.sparta.greeypeople.profile.dto.request.PasswordUpdateRequestDto;
import com.sparta.greeypeople.profile.dto.request.ProfileRequestDto;
import com.sparta.greeypeople.profile.dto.response.ProfileResponseDto;
import com.sparta.greeypeople.profile.entity.PasswordList;
import com.sparta.greeypeople.profile.repository.PasswordListRepository;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sparta.greeypeople.exception.InvalidEnteredException;

import java.util.List;

/**
 * ProfileService는 사용자 프로필 및 비밀번호 변경 관련 비즈니스 로직을 처리
 */
@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordListRepository passwordListRepository;

    public ProfileService(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordListRepository passwordListRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordListRepository = passwordListRepository;
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto getProfile(User user) {
        return new ProfileResponseDto(user);
    }

    @Transactional
    public ProfileResponseDto updateProfile(User user, ProfileRequestDto requestDto) {
        user.updateUserName(requestDto.getUserName());
        user.updateIntro(requestDto.getIntro());
        userRepository.save(user);
        return new ProfileResponseDto(user);
    }

    @Transactional
    public void updatePassword(User user, PasswordUpdateRequestDto requestDto) {
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new InvalidEnteredException("현재 비밀번호와 일치하지 않습니다.");
        }

        List<PasswordList> pastPasswords = passwordListRepository.findByUserOrderByCreatedAtDesc(user);
        String newPasswordEncoded = passwordEncoder.encode(requestDto.getNewPassword());
        if (pastPasswords.stream().anyMatch(pastPassword -> passwordEncoder.matches(requestDto.getNewPassword(), pastPassword.getPassword()))) {
            throw new InvalidEnteredException("새로운 비밀번호는 현재 비밀번호 및 최근 사용한 비밀번호와 달라야 합니다.");
        }

        if (pastPasswords.size() >= 3) {
            PasswordList oldestPassword = pastPasswords.remove(0);
            passwordListRepository.delete(oldestPassword);
        }

        PasswordList newPasswordList = PasswordList.builder()
            .password(newPasswordEncoded)
            .user(user)
            .build();
        passwordListRepository.save(newPasswordList);

        user.updatePassword(newPasswordEncoded);
        userRepository.save(user);
    }
}
