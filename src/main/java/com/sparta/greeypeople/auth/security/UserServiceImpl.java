package com.sparta.greeypeople.auth.security;

import com.sparta.greeypeople.auth.dto.response.TokenResponseDto;
import com.sparta.greeypeople.auth.util.JwtUtil;
import com.sparta.greeypeople.exception.ConflictException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.user.dto.request.LoginRequestDto;
import com.sparta.greeypeople.user.dto.request.SignupRequestDto;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import com.sparta.greeypeople.user.repository.UserRepository;
import com.sparta.greeypeople.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserService 구현체
 * 사용자 회원가입, 로그인, 로그아웃, 회원탈퇴 등의 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 생성자
     * @param userRepository User 리포지토리
     * @param passwordEncoder PasswordEncoder 인코더
     * @param jwtUtil JwtUtil JWT 유틸리티 클래스
     */
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
        JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 사용자 회원가입 처리
     *
     * @param signupRequest 회원가입 요청 DTO
     */
    @Override
    public void signup(SignupRequestDto signupRequest) {
        Optional<User> existingUser = userRepository.findByUserId(signupRequest.getUserId());
        if (existingUser.isPresent()) {
            throw new ConflictException("이미 존재하는 사용자 ID입니다.");
        }

        User user = new User();
        user.updateUserId(signupRequest.getUserId());
        user.updatePassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.updateUserName(signupRequest.getUserName());
        user.updateEmail(signupRequest.getEmail());
        user.updateIntro(signupRequest.getIntro());
        user.updateUserAuth(
            signupRequest.getUserAuth().equals("admin") ? UserAuth.ADMIN : UserAuth.USER);
        userRepository.save(user);
    }

    /**
     * 사용자 로그인 처리 및 토큰 발급
     * @param loginRequest 로그인 요청 DTO
     * @return TokenResponseDto 액세스 및 리프레시 토큰을 포함한 응답 DTO
     */
    @Override
    public TokenResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByUserId(loginRequest.getUserId())
            .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(user.getUserId());
        String refreshToken = jwtUtil.createRefreshToken(user.getUserId());
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    /**
     * 사용자 로그아웃 처리
     * @param userId 사용자 ID
     */
    @Override
    public void logout(String userId) {
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        user.updateRefreshToken(null);
        userRepository.save(user);
    }

    /**
     * 사용자 회원탈퇴 처리
     * @param userId   사용자 ID
     * @param password 비밀번호
     */
    @Override
    public void withdraw(String userId, String password) {
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }

    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
     * @param refreshToken 리프레시 토큰
     * @return TokenResponseDto 새로운 액세스 및 리프레시 토큰을 포함한 응답 DTO
     */
    @Override
    public TokenResponseDto refresh(String refreshToken) {
        return userRepository.findByUserId(jwtUtil.getUsernameFromToken(refreshToken))
            .filter(u -> u.getRefreshToken().equals(refreshToken))
            .map(u -> new TokenResponseDto(jwtUtil.createAccessToken(u.getUserId()), refreshToken))
            .orElseThrow(() -> new IllegalArgumentException("리프레시 토큰이 유효하지 않습니다."));
    }
}