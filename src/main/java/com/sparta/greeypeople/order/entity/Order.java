package com.sparta.greeypeople.order.entity;

import com.sparta.greeypeople.common.TimeStamp;
import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.order.enumeration.Process;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.user.entity.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "order_table")
@NoArgsConstructor
public class Order extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Process process;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    public Order(Store store, List<OrderMenu> orderMenus, User user) {
        this.store = store;
        this.orderMenus = orderMenus;
        this.user = user;
    }

    public void update(List<OrderMenu> orderMenus) {
        this.orderMenus = orderMenus;
    }
}
