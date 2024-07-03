package com.sparta.greeypeople.user.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.UserTest;

import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestDtoTest implements UserTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Locale.setDefault(Locale.KOREAN);
    }

    @Test
    @DisplayName("성공 - 유효성 검사")
    void test1() {
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(TEST_SIGNUP_REQUEST_DTO);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("실패 - 유효성 검사(아이디)")
    void test2() {
        String invalidUserId = "invalidUserId";
        SignupRequestDto invalidDto = new SignupRequestDto(
                invalidUserId,
                TEST_USER_PASSWORD,
                TEST_USER_NAME,
                TEST_USER_EMAIL,
                TEST_USER_INTRO,
                TEST_USER_ADMINTOKEN
        );

        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("사용자 ID는 알파벳 소문자와 숫자로 이루어진 4자에서 10자 사이여야 합니다.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("실패 - 유효성 검사(비밀번호)")
    void test3() {
        String invalidPassword = "invalidPassword";
        SignupRequestDto invalidDto = new SignupRequestDto(
                TEST_USER_ID,
                invalidPassword,
                TEST_USER_NAME,
                TEST_USER_EMAIL,
                TEST_USER_INTRO,
                TEST_USER_ADMINTOKEN
        );

        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("비밀번호는 대소문자 영문, 숫자, 특수문자를 최소 1글자씩 포함하며 최소 8자에서 15자 사이여야 합니다.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("실패 - 유효성 검사(이메일)")
    void test4() {
        String invalidEmail = "invalidEmail";
        SignupRequestDto invalidDto = new SignupRequestDto(
                TEST_USER_ID,
                TEST_USER_PASSWORD,
                TEST_USER_NAME,
                invalidEmail,
                TEST_USER_INTRO,
                TEST_USER_ADMINTOKEN
        );

        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("이메일 형식으로 입력해주세요.", violations.iterator().next().getMessage());
    }

}