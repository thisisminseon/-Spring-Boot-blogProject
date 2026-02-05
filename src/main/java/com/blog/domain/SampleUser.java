package com.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, toString, equals, hashCode, EqualsAndHashCode 자동생성
@NoArgsConstructor // 기본생성자  - 매개변수가 없는 생성자
@AllArgsConstructor // 전체 필드 생성자 - 모든 필드를 매개변수로 받는 생성자 자동 생성
@Builder

public class SampleUser {

	private Long id;
	private String name;
	private Integer age; 
	private String email;
	private String role;
	private Boolean active;
	
	public SampleUser(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
	
	public SampleUser(String name, Integer age, String role) {
		this.name = name;
		this.age = age;
		this.role = role;
	}
}