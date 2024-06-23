package com.sparta.greeypeople.store.entity;

import com.sparta.greeypeople.common.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "store")
public class Store extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name", length = 50, nullable = false)
    private String storeName;

    @Column(name = "intro", nullable = false)
    private String intro;
}
