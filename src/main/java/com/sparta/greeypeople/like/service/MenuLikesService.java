package com.sparta.greeypeople.like.service;

import com.sparta.greeypeople.like.entity.MenuLikes;
import com.sparta.greeypeople.like.repository.MenuLikesRepository;
import com.sparta.greeypeople.menu.repository.MenuRepository;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.greeypeople.exception.BadRequestException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.exception.ViolatedException;
import com.sparta.greeypeople.menu.entity.Menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuLikesService {

	private final MenuLikesRepository menuLikesRepository;
	private final UserRepository userRepository;
	private final MenuRepository menuRepository;

	@Transactional
	public void addMenuLike(Long menuId, User user) {

		User foundUser = userRepository.findById(user.getId()).orElseThrow(() ->
				new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
		Menu foundMenu = menuRepository.findById(menuId).orElseThrow(() ->
			new DataNotFoundException("해당 메뉴가 존재하지 않습니다."));

		if (foundUser.getUserId().equals(foundMenu.getUser().getUserId())) {
			throw new BadRequestException("자신이 등록한 메뉴에는 좋아요를 남길 수 없습니다.");
		}

		MenuLikes menuLikes = new MenuLikes(foundUser, foundMenu);

		if (menuLikesRepository.findByUserAndMenu(foundUser, foundMenu).isPresent()) {
			throw new ViolatedException("이미 좋아요를 누른 메뉴입니다.");
		}

		menuLikesRepository.save(menuLikes);
		foundMenu.addLike();
	}

	@Transactional
	public void removeMenuLike(Long menuLikeId, User user) {

		MenuLikes foundlike = menuLikesRepository.findById(menuLikeId).orElseThrow(
			() -> new DataNotFoundException("해당 좋아요가 존재하지 않습니다."));

		Menu foundMenu = menuRepository.findById(foundlike.getMenu().getId()).orElseThrow(
			() -> new DataNotFoundException("해당 메뉴가 존재하지 않습니다."));

		if (!(user.getUserId().equals(foundlike.getUser().getUserId()))) {
			throw new ForbiddenException("다른 사람의 좋아요는 삭제할 수 없습니다.");
		}

		menuLikesRepository.delete(foundlike);
		foundMenu.minusLike();
	}

}