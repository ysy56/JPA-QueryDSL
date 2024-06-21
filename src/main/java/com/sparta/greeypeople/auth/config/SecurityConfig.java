package com.sparta.greeypeople.auth.config;

import com.sparta.greeypeople.auth.filter.JwtAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * JwtAuthenticationFilter를 주입받는 생성자
     * @param jwtAuthenticationFilter Jwt 인증 필터
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * SecurityFilterChain 빈 등록
     * @param http HttpSecurity 객체
     * @return SecurityFilterChain 인스턴스
     * @throws Exception 설정 중 예외 발생 시
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 정책을 STATELESS로 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // 정적 리소스에 대한 요청 허용
                        .requestMatchers("/", "/index.html", "/api/auth/**").permitAll() // 특정 경로에 대한 요청 허용
                        .requestMatchers(HttpMethod.POST, "/users/**").permitAll() // POST 메서드를 사용한 /users/** 경로 요청 허용
                        .anyRequest().authenticated()) // 그 외의 모든 요청은 인증 필요
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 추가

        return http.build();
    }
}
