package com.sparta.greeypeople.user.repository;

import com.sparta.greeypeople.user.entity.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
    Optional<BlockedUser> findByUserId(Long userId);
}