package com.sparta.greeypeople.user.entity;

import com.sparta.greeypeople.menu.entity.Menu;
import com.sparta.greeypeople.review.entity.Review;
import com.sparta.greeypeople.common.TimeStamp;
import com.sparta.greeypeople.user.dto.request.AdminUserProfileRequestDto;
import com.sparta.greeypeople.user.dto.request.SignupRequestDto;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import com.sparta.greeypeople.user.enumeration.UserStatus;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 엔티티 클래스 데이터베이스의 사용자 정보를 나타내는 클래스
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(unique = true)
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Menu> menu = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Review> comments = new ArrayList<>();

    private Long kakaoId;

    @ElementCollection(fetch = FetchType.EAGER) // FetchType 설정 추가
    @CollectionTable(name = "past_passwords", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "password")
    private List<String> pastPasswords = new ArrayList<>();

    public User(SignupRequestDto requestDto, UserStatus userStatus, UserAuth userAuth) {
        this.userId = requestDto.getUserId();
        this.password = requestDto.getPassword();
        this.userName = requestDto.getUserName();
        this.email = requestDto.getEmail();
        this.intro = requestDto.getIntro();
        this.userStatus = userStatus;
        this.userAuth = userAuth;
        this.pastPasswords.add(this.password);
    }

    public User(String userId, String password, String userName, String email, UserStatus userStatus, UserAuth userAuth, Long kakaoId) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.userStatus = userStatus;
        this.userAuth = userAuth;
        this.kakaoId = kakaoId;
        this.pastPasswords.add(this.password);
    }

    public void updateUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updatePassword(String newPassword) {
        if (this.pastPasswords.size() >= 3) {
            this.pastPasswords.remove(0);
        }
        this.pastPasswords.add(newPassword);
        this.password = newPassword;
    }

    public void encryptionPassword(String password) {
        this.password = password;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public void updateIntro(String intro) {
        this.intro = intro;
    }

    public void updateProfile(AdminUserProfileRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.intro = requestDto.getIntro();
    }

    public void updateAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
