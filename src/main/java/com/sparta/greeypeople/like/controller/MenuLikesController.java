package com.sparta.greeypeople.like.controller;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.like.service.MenuLikesService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.exception.BadRequestException;
import com.sparta.greeypeople.exception.DataNotFoundException;
import com.sparta.greeypeople.exception.ForbiddenException;
import com.sparta.greeypeople.exception.ViolatedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/menus")
public class MenuLikesController {

	private final MenuLikesService menuLikesService;

	@PostMapping("/{menuId}/like")
	public ResponseEntity<StatusCommonResponse> addMenuLike(@PathVariable Long menuId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			menuLikesService.addMenuLike(menuId, userDetails.getUser());
			StatusCommonResponse commonResponse = new StatusCommonResponse(201, "메뉴 좋아요 등록 성공");
			return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
		} catch (BadRequestException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StatusCommonResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
		} catch (ViolatedException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new StatusCommonResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
		}
	}

	@DeleteMapping("/{menuLikeId}/like")
	public ResponseEntity<StatusCommonResponse> removeMenuLike(@PathVariable Long menuLikeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			menuLikesService.removeMenuLike(menuLikeId, userDetails.getUser());
			StatusCommonResponse commonResponse = new StatusCommonResponse(204, "메뉴 좋아요 삭제 성공");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
		} catch (DataNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StatusCommonResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
		} catch (ForbiddenException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new StatusCommonResponse(HttpStatus.FORBIDDEN.value(), e.getMessage()));
		}
	}

}