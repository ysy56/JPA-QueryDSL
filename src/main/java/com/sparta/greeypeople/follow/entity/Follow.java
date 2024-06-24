package com.sparta.greeypeople.follow.entity;

import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.common.TimeStamp;
import com.sparta.greeypeople.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "follow_table")
public class Follow extends TimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public Follow(User user, Store store) {
        this.user = user;
        this.store = store;
    }
}