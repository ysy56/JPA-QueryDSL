package com.sparta.greeypeople.like.repository;

import com.sparta.greeypeople.menu.entity.Menu;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.greeypeople.like.entity.MenuLikes;

@Repository
public interface MenuLikesRepository extends JpaRepository<MenuLikes, Long> {
	Optional<MenuLikes> findByUserAndMenu(User foundUser, Menu foundMenu);
}