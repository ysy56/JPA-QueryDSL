package com.sparta.greeypeople.like.menu;

import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.timestamp.TimeStamp;

import jakarta.persistence.*;
import lombok.Builder;
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

	@Builder
	public MenuLikes(User user, Menu menu) {
		this.user = user;
		this.menu = menu;
	}
}