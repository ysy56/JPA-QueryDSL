package com.sparta.greeypeople.store.entity;

import com.sparta.greeypeople.store.dto.request.AdminStoreSaveRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name", length = 50, nullable = false)
    private String storeName;

    @Column(name = "intro", nullable = false)
    private String intro;

    public Store(AdminStoreSaveRequestDto requestDto) {
        this.storeName = requestDto.getStoreName();
        this.intro = requestDto.getIntro();
    }
}
