package com.sparta.myblog.mockobject;

import com.sparta.myblog.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockUserRepository {

    private List<User> users = new ArrayList<>();
    private Long id = 1L;

    // 회원 name으로 등록된 user 조회
    public Optional<User> findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // 회원 저장
    public User save(User user) {
        user.setId(id);
        ++id;
        users.add(user);
        return user;
    }

}
