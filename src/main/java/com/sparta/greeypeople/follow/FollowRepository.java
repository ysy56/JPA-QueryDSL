package com.sparta.greeypeople.follow;

import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByUserAndStore(User user, Store store);
    List<Store> findFollowedStoresByUser(User user);
}