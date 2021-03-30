package com.sparta.myblog.service;

import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.model.User;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.security.kakao.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import com.sparta.myblog.security.kakao.KakaoOAuth2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";


    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String password_again = requestDto.getPassword_again();

        //닉네임(회원 ID) 표현식 확인
        if(username.length() < 3 || !Pattern.matches("^[a-zA-Z0-9]*$", username)) {
            throw new IllegalArgumentException("닉네임은 최소 3자 이상, 알파벳 대소문자, 숫자로 구성해야 합나다.");
        }

        // 닉네임(회원 ID) 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
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

        // 비밀번호 인코딩
        password = passwordEncoder.encode(password);


        User user = new User(username, password);
        userRepository.save(user);
    }

    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);

            kakaoUser = new User(nickname, encodedPassword, kakaoId);
            userRepository.save(kakaoUser);
        }
        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}