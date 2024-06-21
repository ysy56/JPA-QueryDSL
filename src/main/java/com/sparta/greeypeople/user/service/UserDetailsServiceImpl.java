package com.sparta.greeypeople.user.service;

import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserDetailsService 구현체: 사용자 인증을 위한 서비스.
 * Spring Security에서 사용자의 인증 및 권한 부여를 처리
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자 ID를 기반으로 사용자 정보를 조회하여 UserDetails 객체를 반환
     * @param userId 사용자 ID
     * @return UserDetails 사용자 세부 정보
     * @throws UsernameNotFoundException 사용자를 찾을 수 없을 때 발생
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
        }
        User user = userOptional.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }
}