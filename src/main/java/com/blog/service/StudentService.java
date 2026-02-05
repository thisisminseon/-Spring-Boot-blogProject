package com.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.domain.Student;
import com.blog.mapper.StudentMapper;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class StudentService {

	// 주입 : "StudentService에서 StudentMapper를 사용하겠다"라고 주입해준다
	private final StudentMapper studentMapper;
	
	// 학생추가
	public void addStudent(Student student) {
		studentMapper.insertStudent(student);
	}
	
	// 전체 학생 검색
	public List<Student> getAllStudents() {
		List<Student> list = studentMapper.selectAllStudents();
		return list;
	}
	
	// id로 검색하기
	public Student selectById(Integer id) {
		return studentMapper.selectById(id);
	}
	
	// 학생 정보 수정
	public void modifyStudent(Student student) {
		studentMapper.updateStudent(student);
	}
	
	// 학생 정보 삭제
	public void removeStudent(Integer id) {
		studentMapper.deleteStudent(id);
	}
}
