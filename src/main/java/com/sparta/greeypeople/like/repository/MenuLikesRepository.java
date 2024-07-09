package com.sparta.greeypeople.like.repository;

import com.sparta.greeypeople.like.entity.MenuLikes;
import com.sparta.greeypeople.menu.dto.response.MenuResponseDto;
import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.user.entity.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuLikesRepository extends JpaRepository<MenuLikes, Long>,
		QuerydslPredicateExecutor<MenuResponseDto>, MenuLikesRepositoryQuery {

	Optional<MenuLikes> findByUserAndMenu(User foundUser, Menu foundMenu);
	
}