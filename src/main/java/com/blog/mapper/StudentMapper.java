package com.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.blog.domain.Student;

/*
 *  @Mapper : StudentMapper 인터페이스는 /resource/mapper와 연동하는 interface
 *  mybatis가 이 인터페이스를 보고 자동으로 구현체를 생성한다
 *  XML 파일의 SQL과 메서드가 자동 연결 된다
 */
@Mapper
public interface StudentMapper {

	// 학생정보 추가 (insert)
	public abstract void insertStudent(Student student);

	// 모든학생 조회 (select all)
	List<Student> selectAllStudents();
	
	// id로 학생 조회
	Student selectById(Integer id);
	
	// 학생 정보 수정
	void updateStudent(Student student);
	
	// 학생 삭제
	void deleteStudent(Integer id);
}














