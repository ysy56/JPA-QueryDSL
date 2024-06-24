package com.sparta.greeypeople.profile.repository;

import com.sparta.greeypeople.profile.entity.PasswordList;
import com.sparta.greeypeople.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * PasswordList 엔티티를 위한 리포지토리 인터페이스
 */
public interface PasswordListRepository extends JpaRepository<PasswordList, Long> {
    List<PasswordList> findByUserOrderByCreatedAtDesc(User user);
}
