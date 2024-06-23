package com.sparta.greeypeople.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.auth.util.JwtUtil;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.user.dto.request.LoginRequestDto;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import com.sparta.greeypeople.user.enumeration.UserStatus;
import com.sparta.greeypeople.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("잘못된 http 요청입니다.");
        }

        try {

            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUserId(),
                    requestDto.getPassword(), null));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {

        String userId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        String userName = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserName();
        UserAuth userAuth = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserAuth();

        Optional<User> user = userRepository.findByUserId(userId);

        if (user.isEmpty() || user.get().getUserStatus().equals(UserStatus.WITHDRAWN)) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("아이디, 비밀번호를 확인해주세요.");

            return;
        }

        String accessToken = jwtUtil.generateAccessToken(userId, userName, userAuth);
        String refreshToken = jwtUtil.generateRefreshToken(userId, userName, userAuth);
        ResponseCookie refreshTokenCookie = jwtUtil.generateRefreshTokenCookie(refreshToken);

        user.get().updateRefreshToken(refreshToken);
        userRepository.save(user.get());

        StatusCommonResponse commonResponse = new StatusCommonResponse(200, "로그인 성공");

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(commonResponse));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("아이디, 비밀번호를 확인해주세요.");
    }

}