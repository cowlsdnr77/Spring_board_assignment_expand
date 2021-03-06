package com.sparta.myblog.mockobject;

import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.model.User;

import java.util.Optional;
import java.util.regex.Pattern;

public class MockUserService {

    //멤버 변수 선언
    private final MockUserRepository mockUserRepository;

    // 생성자: MockUserService() 가 생성될 떄 호출됨
    public MockUserService() {
        this.mockUserRepository = new MockUserRepository();
    }

    public User registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String password_again = requestDto.getPassword_again();

        //닉네임(회원 ID) 표현식 확인
        if(username.length() < 3 || !Pattern.matches("^[a-zA-Z0-9]*$", username)) {
            throw new IllegalArgumentException("닉네임은 최소 3자 이상, 알파벳 대소문자, 숫자로 구성해야 합나다.");
        }

        // 닉네임(회원 ID) 중복 확인
        Optional<User> found = mockUserRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        //비밀번호 표현식 확인
        if(password.length() < 4 || password.contains(username)) {
            throw new IllegalArgumentException("비밀번호는 최소 4자 이상, 닉네임을 포함하지 않고 구성해야 합니다.");
        }

        // 비밀번호와 비밀번호 확인 일치 여부 확인
        if(!password.equals(password_again)) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }


        User user = new User(username, password);
        mockUserRepository.save(user);

        return user;
    }
}
