package com.sparta.greeypeople.like.menu;

import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.common.TimeStamp;

import com.sparta.greeypeople.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "menuLikes_table")
@NoArgsConstructor
public class MenuLikes extends TimeStamp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id", nullable = false)
	private Menu menu;

	public MenuLikes(User user, Menu menu) {
		this.user = user;
		this.menu = menu;
	}
}