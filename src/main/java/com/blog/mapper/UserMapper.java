package com.blog.mapper;

import com.blog.domain.User;
import org.apache.ibatis.annotations.Mapper;

// User Mapper Interface
// User Information Inquiry

@Mapper
public interface UserMapper {

	// inquiry by username
	// @param username 사용자명
	// @return User 객체
	User findByUsername(String username);

	// add user (sign up)
	// @param user 사용자 객체
	void insertUser(User user);

	// Verify user name duplication
	// @param username 사용자명
	// @return 존재하면 1, 없으면 0
	int existsByUsername(String username);
}