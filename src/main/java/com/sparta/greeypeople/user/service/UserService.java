package com.sparta.greeypeople.user.service;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.auth.util.JwtUtil;
import com.sparta.greeypeople.exception.BadRequestException;
import com.sparta.greeypeople.exception.ConflictException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.UnauthorizedException;
import com.sparta.greeypeople.user.dto.request.PasswordRequestDto;
import com.sparta.greeypeople.user.dto.request.SignupRequestDto;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import com.sparta.greeypeople.user.enumeration.UserStatus;
import com.sparta.greeypeople.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
        JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void signup(SignupRequestDto requestDto) {

        findByUserId(requestDto.getUserId()).ifPresent((el) -> {
            throw new ConflictException("이미 사용 중인 아이디입니다.");
        });

        UserAuth userAuth = UserAuth.USER;
        if (!requestDto.getAdminToken().isEmpty()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            userAuth = UserAuth.ADMIN;
        }

        UserStatus userStatus = UserStatus.ACTIVE;
        User user = new User(requestDto, userStatus, userAuth);
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        user.encryptionPassword(encodedPassword);
        userRepository.save(user);

    }

    @Transactional
    public void logout() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = findByUserId(userDetails.getUsername()).orElseThrow(
            () -> new DataNotFoundException("해당 회원은 존재하지 않습니다."));

        user.updateRefreshToken(null);

    }

    @Transactional
    public void withdrawal(PasswordRequestDto requestDto) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = findByUserId(userDetails.getUsername()).orElseThrow(
            () -> new DataNotFoundException("해당 회원은 존재하지 않습니다."));

        if (!checkPassword(requestDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("비밀번호를 확인해주세요.");
        }

        if (user.getUserStatus() == UserStatus.WITHDRAWN) {
            throw new ConflictException("이미 탈퇴한 회원입니다.");
        }

        UserStatus userStatus = UserStatus.WITHDRAWN;

        user.updateUserStatus(userStatus);

    }

    @Transactional
    public HttpHeaders refresh(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String tokenValue = null;

        if (cookies == null) {
            throw new BadRequestException("잘못된 요청입니다.");
        }

        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                tokenValue = cookie.getValue();
            }
        }

        if (!StringUtils.hasText(tokenValue)) {
            throw new BadRequestException("잘못된 요청입니다.");
        }

        jwtUtil.checkTokenExpiration(tokenValue);

        if (!jwtUtil.validateToken(tokenValue)) {
            throw new UnauthorizedException("토큰 검증 실패");
        }

        Claims info = jwtUtil.getClaimsFromToken(tokenValue);
        User user = findByUserId(info.getSubject()).orElseThrow(
            () -> new DataNotFoundException("해당 회원은 존재하지 않습니다."));

        if (!user.getRefreshToken().equals(tokenValue)) {
            throw new UnauthorizedException("토큰 검증 실패");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUserId(), user.getUserName(), user.getUserAuth());
        String refreshToken = jwtUtil.generateNewRefreshToken(user.getUserId(), user.getUserName(), user.getUserAuth(),
            info.getExpiration());
        ResponseCookie responseCookie = jwtUtil.generateNewRefreshTokenCookie(refreshToken,
            info.getExpiration());

        user.updateRefreshToken(refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

        return headers;
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public boolean checkPassword(String requestPassword, String userPassword) {
        return passwordEncoder.matches(requestPassword, userPassword);
    }

}
