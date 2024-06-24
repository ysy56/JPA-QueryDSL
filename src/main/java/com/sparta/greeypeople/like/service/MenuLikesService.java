package com.sparta.greeypeople.like.service;

import com.sparta.greeypeople.exception.BadRequestException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.exception.ViolatedException;
import com.sparta.greeypeople.like.entity.MenuLikes;
import com.sparta.greeypeople.like.repository.MenuLikesRepository;
import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.menu.repository.MenuRepository;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.store.repository.StoreRepository;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuLikesService {

	private final MenuLikesRepository menuLikesRepository;
	private final UserRepository userRepository;
	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;

	@Transactional
	public void addMenuLike(Long storeId, Long menuId, User user) {
		User foundUser = validateUser(user);
		Menu foundMenu = validateStoreAndMenu(storeId, menuId);

		if (foundUser.getUserId().equals(foundMenu.getUser().getUserId())) {
			throw new BadRequestException("자신이 등록한 메뉴에는 좋아요를 남길 수 없습니다.");
		}

		if (menuLikesRepository.findByUserAndMenu(foundUser, foundMenu).isPresent()) {
			throw new ViolatedException("이미 좋아요를 누른 메뉴입니다.");
		}

		MenuLikes menuLikes = new MenuLikes(foundUser, foundMenu);
		menuLikesRepository.save(menuLikes);
		foundMenu.addLike();
	}

	@Transactional
	public void removeMenuLike(Long storeId, Long menuLikeId, User user) {
		User foundUser = validateUser(user);
		MenuLikes foundLike = menuLikesRepository.findById(menuLikeId).orElseThrow(
			() -> new DataNotFoundException("해당 좋아요가 존재하지 않습니다."));
		Menu foundMenu = validateStoreAndMenu(storeId, foundLike.getMenu().getId());

		if (!foundUser.getUserId().equals(foundLike.getUser().getUserId())) {
			throw new ForbiddenException("다른 사람의 좋아요는 삭제할 수 없습니다.");
		}

		menuLikesRepository.delete(foundLike);
		foundMenu.minusLike();
	}

	private User validateUser(User user) {
		return userRepository.findById(user.getId()).orElseThrow(() ->
			new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
	}

	private Menu validateStoreAndMenu(Long storeId, Long menuId) {
		Store store = storeRepository.findById(storeId).orElseThrow(() ->
			new DataNotFoundException("해당 가게가 존재하지 않습니다."));
		Menu foundMenu = menuRepository.findById(menuId).orElseThrow(() ->
			new DataNotFoundException("해당 메뉴가 존재하지 않습니다."));

		if (!foundMenu.getStore().getId().equals(store.getId())) {
			throw new BadRequestException("메뉴가 해당 가게에 존재하지 않습니다.");
		}

		return foundMenu;
	}
}
