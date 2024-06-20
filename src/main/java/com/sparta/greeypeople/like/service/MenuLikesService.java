package com.sparta.greeypeople.like.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.greeypeople.exception.BadRequestException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.exception.ViolatedException;
import com.sparta.greeypeople.like.entity.MenuLikes;
import com.sparta.greeypeople.like.repository.MenuLikesRepository;
import com.sparta.greeypeople.menu.entity.Menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuLikesService {

	private final MenuLikesRepository menuLikesRepository;
	private final UserService userService;
	private final MenuService menuService;

	@Transactional
	public void addMenuLike(Long menuId, User user) {

		User foundUser = userService.findById(user.getId());
		Menu foundMenu = menuService.findById(menuId);

		if (foundUser.getUserId().equals(foundMenu.getUser().getUserId())) {
			throw new BadRequestException("자신이 등록한 메뉴에는 좋아요를 남길 수 없습니다.");
		}

		var MenuLikes = new MenuLikes(foundUser, foundMenu);

		if (menuLikesRepository.findByUserAndMenu(foundUser, foundMenu).isPresent()) {
			throw new ViolatedException("이미 좋아요를 누른 메뉴입니다.");
		}

		menuLikesRepository.save(MenuLikes);
		foundMenu.addLike();
	}

	@Transactional
	public void removeMenuLike(Long menuLikeId, User user) {

		MenuLikes foundlike = menuLikesRepository.findById(menuLikeId).orElseThrow(
			() -> new DataNotFoundException("해당 좋아요가 존재하지 않습니다."));

		Menu foundMenu = menuService.findById(foundlike.getMenu().getId());

		if (!(user.getUserId().equals(foundlike.getUser().getUserId()))) {
			throw new ForbiddenException("다른 사람의 좋아요는 삭제할 수 없습니다.");
		}

		menuLikesRepository.delete(foundlike);
		foundMenu.minusLike();
	}

}