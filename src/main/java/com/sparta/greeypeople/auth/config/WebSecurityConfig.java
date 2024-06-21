package com.sparta.greeypeople.auth.config;

import com.sparta.greeypeople.auth.filter.JwtAuthenticationFilter;
import com.sparta.greeypeople.auth.filter.JwtAuthorizationFilter;
import com.sparta.greeypeople.auth.security.UserDetailsServiceImpl;
import com.sparta.greeypeople.auth.util.JwtUtil;
import com.sparta.greeypeople.user.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserRepository userRepository;

    public WebSecurityConfig(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, AuthenticationConfiguration authenticationConfiguration, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.userRepository = userRepository;
        // this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, userRepository);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));

        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf( (csrf) -> csrf.disable());

        http.sessionManagement( (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests( (authorizeHttpRequests) ->
            authorizeHttpRequests
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/users/login", "/users/signup", "/api/users/refresh").permitAll()
                .requestMatchers(HttpMethod.GET, "/orders", "/stores").permitAll()
                .anyRequest().authenticated()
        );

//        http.exceptionHandling( (exceptionHandling) -> {
//            exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint);
//        });

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }
}
