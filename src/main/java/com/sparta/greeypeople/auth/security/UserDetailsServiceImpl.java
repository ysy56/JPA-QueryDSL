package com.sparta.greeypeople.auth.security;

import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(userId).orElseThrow( () -> new UsernameNotFoundException("아이디, 비밀번호를 확인해주세요."));

        return new UserDetailsImpl(user);
    }

}

