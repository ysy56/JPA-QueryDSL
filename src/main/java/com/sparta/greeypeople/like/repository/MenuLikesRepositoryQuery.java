package com.sparta.greeypeople.like.repository;

import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuLikesRepositoryQuery {

    public Page<Menu> findLikedMenus(User user, Pageable pageable);

}
