package com.sparta.greeypeople.auth.filter;

import com.sparta.greeypeople.user.service.UserDetailsServiceImpl;
import com.sparta.greeypeople.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 인증 필터: 모든 HTTP 요청이 이 필터를 거침
 * 클라이언트로부터 HTTP 요청을 가로채서 JWT 토큰을 검사하고,
 * 유효한 토큰이면 사용자 정보를 SecurityContext에 저장
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 요청 필터링: JWT 토큰을 검증해서 유효하면 사용자 정보를 설정
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @param chain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException 입출력 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 요청 헤더에서 Authorization 추출
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // authorizationHeader가 존재하고 "Bearer "로 시작할 경우
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // "Bearer " 이후의 JWT 토큰 부분
            username = jwtUtil.getUsernameFromToken(jwt); // JWT 토큰에서 사용자 이름 추출
        }

        // 사용자 이름이 존재하고 현재 SecurityContext에 인증 정보가 없는 경우
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // JWT 토큰이 유효한 경우
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // 다음 필터로 요청을 전달
        chain.doFilter(request, response);
    }
}
