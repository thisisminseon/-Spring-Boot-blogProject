package com.blog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.blog.domain.Student;
import com.blog.service.StudentService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/student")
public class StudentAPIController {

	// 서비스 주입
	private final StudentService studentService;
	
	@GetMapping("/api")
	public String home() {
		return "/student/list";
	}
	
	@PostMapping("/api/insert")
	@ResponseBody
	public Student createStudent(@RequestBody Student student) {
	    studentService.addStudent(student);
	    return student;
	 // RestController의 return type이 String이면 문자열을 클라이언트로 보낸다
	 // Controller이고 return type이 String이면 .html을 실행한다
	}

	
	// 모든학생 조회
	// http://localhost:8089/student/list
	@GetMapping("/api/list")
	@ResponseBody
	public List<Student> getAllStudents() {
	    List<Student> list = studentService.getAllStudents();
	    return list;
	}
	
	
	/* 
	 * postman 으로 테스트할 때 http://localhost:8089/student/postmanlist
	 * @ResponseBody 자바객체를 JSON으로 변환
	 * @ResponseBody 를 사용하는 이유는 RestController가 아닌 Controller를 사용 중이기 때문임
	 * @RequestBody : JSON → java 변환
	 * @RestController : Controller + Responsebody
	 */
	
	@GetMapping("/api/postmanlist")
	// @ResponseBody
	public List<Student> getAllStudentsPostman(Model model) {
	    List<Student> list = studentService.getAllStudents();
	    model.addAttribute("list", list);
	    return list;
	}
	
	
	/*
	 * 특정학생 조회
	 * url : http://localhost:8089/student/2
	 * @pathVariavle : URL경로의 {id}값을 파라미터로 받음
	 * 결과는 Student 객체를 JSON 데이터 형식으로 변환하여 보내진다. 왜? 제일 위에 @RestController 선언이 있기 때문에
	 */
	
	@GetMapping("/api/{id}")
	@ResponseBody
	public Student getStudent(@PathVariable("id") Integer Id) {
		return studentService.selectById(Id);
	}
	
	
	/*
	 * 학생 수정
	 * url : http://localhost:8089/student/2
	 * 요청 body : 수정할 정보가 담긴 JSON
	 */
	
	@PutMapping("/api/{id}")
	@ResponseBody
	public Student updateStudent(@PathVariable("id") Integer id,
			@RequestBody Student student) {
	
		student.setId(id);
		studentService.modifyStudent(student);
		return student;
	}
	
	
	/*
	 * 학생 삭제
	 * url : http://localhost:8089/student/1 
	 */
	
	@DeleteMapping("/api/{id}")
	@ResponseBody
	public void deleteStudent(@PathVariable("id") Integer id) {
	    studentService.removeStudent(id);
	}
	
	
	/*
	 * 핵심 개념 정리
	 * MyBatis 역할 : SQL을 자바 코드에서 분리하여 XML로 관리
	 * Mapper Interface : SQL을 호출하는 메서드 정의
	 * Mapper XML : 실제 SQL 쿼리 작성
	 * #{변수} : PreparedStatement의 ? 역할, 안전하게 값 바인딩
	 * @Mapper : Spring이 자동으로 구현체 생성
	 */
}
