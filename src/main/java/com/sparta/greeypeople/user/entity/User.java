package com.sparta.greeypeople.user.entity;

import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 사용자 엔티티 클래스 데이터베이스의 사용자 정보를 나타내는 클래스
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String userName;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(length = 100)
    private String intro;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserAuth userAuth;

    @Column(unique = true)
    private String refreshToken;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Menu> menu = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public User(String userId, String password, String userName, String email, UserAuth userAuth) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.userAuth = userAuth;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateUserId(String userId) {
        this.userId = userId;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateIntro(String intro) {
        this.intro = intro;
    }

    public void updateUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
