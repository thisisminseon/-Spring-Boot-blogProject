package com.blog.service;

import com.blog.domain.User;
import com.blog.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


// UserService
// 회원가입, 비밀번호 암호화 처리

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    // * 패스워드 암호화 →　PasswordEncoder
    private final PasswordEncoder passwordEncoder;

     // 회원가입
     // @param user 사용자 정보
     // @return 성공 여부

    public boolean registerUser(User user) {
        // 1. 사용자명 중복 확인
        if (userMapper.existsByUsername(user.getUsername()) > 0) {
            return false;  // Username already exists
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // 3. 기본값 설정
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");  // Default Permissions
        }
        user.setEnabled(true);

        // 4. DB에 저장
        userMapper.insertUser(user);
        return true;
    }


     // 사용자명으로 조회
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    // 사용자명 중복 확인
    public boolean isUsernameTaken(String username) {
        return userMapper.existsByUsername(username) > 0;
    }
}