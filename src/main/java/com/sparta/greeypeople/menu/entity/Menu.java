package com.sparta.greeypeople.menu.entity;

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

    @Column(name = "menu", length = 50 , nullable = false)
    private String menu;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id", nullable = false)
//    private Store store;

    @Column
    private Long menuLikes;

    public void addLike() {
        this.menuLikes = menuLikes + 1L;
    }

    public void minusLike() {
        this.menuLikes = menuLikes - 1L;
    }
}
