package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/* 
 * csrf(Cross Site Request Forgery)
 * CSRF는 웹 보안 취약점의 일종이며, 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위(데이터 수정, 삭제, 등록 등) 을 특정 웹사이트에 요청하게 하는 공격
 * 예시로, 피해자의 전자 메일 주소를 변경하거나 암호를 변경하거나 자금이체를 하는 등의 동작을 수행하게 할 수 있다
 */

@Configuration // Informs SpringBoot about the authentication and authorization blueprint ・ configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain fileterChain(HttpSecurity http) throws Exception {

		http
		.csrf(csrf -> csrf.disable()) // Turn .csrf Off
		.authorizeHttpRequests(auth -> auth // auth is 'Parameter'
				.anyRequest().permitAll() // Permitting All url
				);

		return http.build();
	}
}
