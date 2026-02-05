package com.blog.domain;

import lombok.Data;

/*
 * Student Class
 * 학생 정보를 담는 자바객체(Dto, VO 역할)
 */

@Data // getter, setter, toString 등 자동생성
public class Student {

	private Integer id; // Integer 사용이유? null값을 허용하기 위해 → int는 null 불가
	private String name;
	private Integer age;
	private String major;
	
}
