package com.sparta.greeypeople.order.entity;

import com.sparta.greeypeople.order.enumeration.Process;
import com.sparta.greeypeople.store.entity.Store;
import com.sparta.greeypeople.timestamp.TimeStamp;
import com.sparta.greeypeople.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany(mappedBy = "order_table")
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
