package com.sparta.greeypeople.like.controller;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.like.service.MenuLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/menus")
public class MenuLikesController {

	private final MenuLikesService menuLikesService;


	/**
	 * 좋아요 등록 기능
	 *
	 * @param menuId      : 좋아요 등록 할 메뉴의 Id
	 * @return : 좋아요 등록 메시지 상태 코드 반환
	 */
	@PostMapping("/{menuId}/like")
	public ResponseEntity<StatusCommonResponse> addMenuLike(@PathVariable Long menuId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		menuLikesService.addMenuLike(menuId, userDetails.getUser());
		StatusCommonResponse commonResponse = new StatusCommonResponse(201, "메뉴 좋아요 등록 성공");
		return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
	}

	/**
	 * 좋아요 삭제 기능
	 *
	 * @param menuLikeId      : 좋아요 삭제 할 메뉴의 좋아요 Id
	 * @return : 좋아요 삭제 메시지 상태 코드 반환
	 */
	@DeleteMapping("/{menuLikeId}/like")
	public ResponseEntity<StatusCommonResponse> removeMenuLike(@PathVariable Long menuLikeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		menuLikesService.removeMenuLike(menuLikeId, userDetails.getUser());
		StatusCommonResponse commonResponse = new StatusCommonResponse(204, "메뉴 좋아요 삭제 성공");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
	}

}