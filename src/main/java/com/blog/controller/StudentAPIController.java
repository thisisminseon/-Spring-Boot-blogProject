package com.blog.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	}
	
	@PostMapping(
			value = "/api/file/insert",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	@ResponseBody
	public ResponseEntity<Student> createStudentWithFile(
			@ModelAttribute Student student,
			@RequestPart(value = "file", required = false) MultipartFile file
	) {
		try {
			studentService.addStudentWithFile(student, file);
			return ResponseEntity.status(HttpStatus.CREATED).body(student);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 파일 포함 수정
	@PutMapping(
			value = "/api/file/{id}",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	@ResponseBody
	public Student updateStudentWithFile(
			@PathVariable("id") Integer id,
			@ModelAttribute Student student,
			@RequestPart(value = "file", required = false) MultipartFile file
	) throws Exception {

		student.setId(id);
		studentService.modifyStudentWithFile(student, file);
		return student;
	}
	
	// 모든학생 조회
	// http://localhost:8089/student/list
	@GetMapping("/api/list")
	@ResponseBody
	public List<Student> getAllStudents() {
	    return studentService.getAllStudents();
	}
	
	@GetMapping("/api/postmanlist")
	public List<Student> getAllStudentsPostman(Model model) {
	    List<Student> list = studentService.getAllStudents();
	    model.addAttribute("list", list);
	    return list;
	}
	
	/*
	 * 특정학생 조회
	 * url : http://localhost:8089/student/2
	 * @pathVariavle : URL경로의 {id}값을 파라미터로 받음
	 */
	@GetMapping("/api/{id}")
	@ResponseBody
	public Student getStudent(@PathVariable("id") Integer id) {
		return studentService.getStudentById(id);
	}
	
	/*
	 * 학생 수정(JSON)
	 */
	@PutMapping("/api/{id}")
	@ResponseBody
	public Student updateStudent(
			@PathVariable("id") Integer id,
			@RequestBody Student student
	) {
		student.setId(id);
		studentService.modifyStudent(student);
		return student;
	}
	
	/*
	 * 학생 삭제
	 */
	@DeleteMapping("/api/{id}")
	@ResponseBody
	public String deleteStudent(@PathVariable("id") Integer id) {
	    studentService.removeStudent(id);
	    return "학생이 삭제 되었습니다";
	}
	
	// 파일 이미지 출력
	@GetMapping("/file/image/{id}")
	public ResponseEntity<Resource> showImage(@PathVariable("id") int id) throws Exception {

		Student student = studentService.getStudentById(id);

		if (student == null || student.getFilePath() == null) {
			System.out.println("[IMAGE] 학생 정보 또는 파일 경로 없음 - id=" + id);
			return ResponseEntity.notFound().build();
		}

		File file = new File(student.getFilePath());
		if (!file.exists()) {
			System.out.println("[IMAGE] 파일이 존재하지 않음 - path=" + student.getFilePath());
			return ResponseEntity.notFound().build();
		}

		// 성공 로그
		System.out.println("[IMAGE] 이미지 로드 성공 ㅋㅋ - id=" + id + ", file=" + file.getName());

		Resource resource = new FileSystemResource(file);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()))
				.body(resource);
	}
	
}