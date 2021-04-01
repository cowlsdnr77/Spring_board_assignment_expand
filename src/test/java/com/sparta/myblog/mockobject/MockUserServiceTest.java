package com.sparta.myblog.mockobject;

import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.model.User;
import com.sparta.myblog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockUserServiceTest {

    MockUserService mockUserService = new MockUserService();

    @Nested
    @DisplayName("회원 가입 검증 테스트")
    class CreateUser {

        private String username;
        private String password;
        private String password_again;

        @BeforeEach
        void setup() {
            username = "rtan1";
            password = "1234";
            password_again = "1234";
        }

        @Test
        @DisplayName("정상 케이스")
        void createUser() {
            //given
            SignupRequestDto requestDto = new SignupRequestDto(username, password, password_again);
            //when
            User user = mockUserService.registerUser(requestDto);
            //then
            assertEquals(username, user.getUsername());
            assertEquals(password, user.getPassword());
        }

        @Nested
        @DisplayName("실패 케이스")
        class FailCases {

            @Nested
            @DisplayName("닉네임 입력 검증")
            class username {

                @Test
                @DisplayName("3자 미만")
                void fail1() {
                    //given
                    username = "hi";
                    SignupRequestDto requestDto = new SignupRequestDto(username, password, password_again);
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        User user = mockUserService.registerUser(requestDto);
                    });
                    //then
                    assertEquals("닉네임은 최소 3자 이상, 알파벳 대소문자, 숫자로 구성해야 합나다.", exception.getMessage());
                }

                @Test
                @DisplayName("한글 입력")
                void fail2() {
                    //given
                    username = "홍길동";
                    SignupRequestDto requestDto = new SignupRequestDto(username, password, password_again);
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        User user = mockUserService.registerUser(requestDto);
                    });
                    //then
                    assertEquals("닉네임은 최소 3자 이상, 알파벳 대소문자, 숫자로 구성해야 합나다.", exception.getMessage());
                }
            }

            @Nested
            @DisplayName("중복된 닉네임 검증")
            class duplicate_username {

                @Test
                @DisplayName("db에 이미 닉네임: rtan1 존재")
                void fail1() {
                    //given
                    SignupRequestDto dup_requestDto = new SignupRequestDto("rtan1", "1212", "1212");
                    User dup_user = mockUserService.registerUser(dup_requestDto);
                    SignupRequestDto requestDto = new SignupRequestDto(username, password, password_again);
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        User user = mockUserService.registerUser(requestDto);
                    });
                    //then
                    assertEquals("중복된 닉네임입니다.", exception.getMessage());
                }

                @Test
                @DisplayName("입력된 닉네임이 rtan2 일때 db에 이미 닉네임: rtan2 존재")
                void fail2() {
                    //given
                    SignupRequestDto dup_requestDto = new SignupRequestDto("rtan2", "1212", "1212");
                    User dup_user = mockUserService.registerUser(dup_requestDto);
                    username = "rtan2";
                    SignupRequestDto requestDto = new SignupRequestDto(username, password, password_again);
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        User user = mockUserService.registerUser(requestDto);
                    });
                    //then
                    assertEquals("중복된 닉네임입니다.", exception.getMessage());
                }
            }

            @Nested
            @DisplayName("비밀번호 입력 검증")
            class password {

                @Test
                @DisplayName("4자 미만")
                void fail1() {
                    //given
                    password = "aaa";
                    SignupRequestDto requestDto = new SignupRequestDto(username, password, password_again);
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        User user = mockUserService.registerUser(requestDto);
                    });
                    //then
                    assertEquals("비밀번호는 최소 4자 이상, 닉네임을 포함하지 않고 구성해야 합니다.", exception.getMessage());
                }

                @Test
                @DisplayName("닉네임과 같은 값 포함")
                void fail2() {
                    //given
                    password = "hirtan1";
                    SignupRequestDto requestDto = new SignupRequestDto(username, password, password_again);
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        User user = mockUserService.registerUser(requestDto);
                    });
                    //then
                    assertEquals("비밀번호는 최소 4자 이상, 닉네임을 포함하지 않고 구성해야 합니다.", exception.getMessage());
                }
            }

            @Nested
            @DisplayName("비밀번호 확인 검증")
            class password_again {

                @Test
                @DisplayName("비밀번호와 일치하지 않음(비밀번호 확인 값이 비밀번호와 같은 길이의 다른 값일때)")
                void fail1() {
                    //given
                    password_again = "1233";
                    SignupRequestDto requestDto = new SignupRequestDto(username, password, password_again);
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        User user = mockUserService.registerUser(requestDto);
                    });
                    //then
                    assertEquals("비밀번호가 올바르지 않습니다.", exception.getMessage());
                }

                @Test
                @DisplayName("비밀번호와 일치하지 않음(비밀번호 확인 값이 없을때)")
                void fail2() {
                    //given
                    password_again = "";
                    SignupRequestDto requestDto = new SignupRequestDto(username, password, password_again);
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                        User user = mockUserService.registerUser(requestDto);
                    });
                    //then
                    assertEquals("비밀번호가 올바르지 않습니다.", exception.getMessage());
                }
            }
        }
    }
}