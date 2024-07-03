package test;

import com.sparta.greeypeople.user.dto.request.SignupRequestDto;
import com.sparta.greeypeople.user.entity.User;
import com.sparta.greeypeople.user.enumeration.UserAuth;
import com.sparta.greeypeople.user.enumeration.UserStatus;

public interface UserTest {
    String TEST_USER_ID = "user1234";
    String TEST_USER_PASSWORD = "user1234!!";
    String TEST_USER_NAME = "username";
    String TEST_USER_EMAIL = "user1234@test.com";
    String TEST_USER_INTRO = "해당 계정은 사용자 계정입니다.";
    String TEST_USER_ADMINTOKEN = "";
    UserStatus TEST_USER_STATUS = UserStatus.ACTIVE;
    UserAuth TEST_USER_AUTH = UserAuth.USER;

    SignupRequestDto TEST_SIGNUP_REQUEST_DTO = new SignupRequestDto(
            TEST_USER_ID,
            TEST_USER_PASSWORD,
            TEST_USER_NAME,
            TEST_USER_EMAIL,
            TEST_USER_INTRO,
            TEST_USER_ADMINTOKEN
    );

    // User TEST_USER = new User(TEST_SIGNUP_REQUEST_DTO, TEST_USER_STATUS, TEST_USER_AUTH);

}