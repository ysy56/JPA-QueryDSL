package com.sparta.greeypeople.menu.entity;

import com.sparta.greeypeople.menu.dto.request.AdminMenuSaveRequestDto;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "menu")
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu", length = 50, nullable = false)
    private String menu;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @Column
    private Long menuLikes;

    public Menu(AdminMenuSaveRequestDto requestDto, User user, Store store) {
        this.menu = requestDto.getMenuName();
        this.price = requestDto.getPrice();
        this.user = user;
        this.store = store;
    }

    public void addLike() {
        this.menuLikes = menuLikes + 1L;
    }

    public void minusLike() {
        this.menuLikes = menuLikes - 1L;
    }

}
