package com.blog.domain;

import lombok.Data;

// USER domain Class
// User Information and Role Management

@Data
public class User {
    
    private Long id;              // 사용자 ID
    private String username;      // 사용자명 (로그인 ID)
    private String password;      // 비밀번호 (암호화됨)
    private String email;         // 이메일
    private String role;          // 권한 (ROLE_USER, ROLE_ADMIN)
    private boolean enabled;      // 계정 활성화 여부
}