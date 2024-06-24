package com.sparta.greeypeople.follow.repository;

import com.sparta.greeypeople.follow.entity.Follow;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByUserAndStore(User user, Store store);

    @Query("SELECT f.store.id FROM Follow f WHERE f.user = :user")
    List<Long> findFollowedStoreIdsByUser(User user);

    List<Follow> findAllByStoreId(Long storeId);
}