package com.blog.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.domain.Student;
import com.blog.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

	// 주입 : StudentService에서 StudentMapper를 사용
	private final StudentMapper studentMapper;

	@Value("${file.upload.path}")
	private String uploadPath;

	/*
	 * 학생추가 메서드
	 */
	public void addStudent(Student student) {
		studentMapper.ainsertStuent(student);
	}

	/*
	 * 모든 학생 조회
	 */
	public List<Student> getAllStudents() {
		return studentMapper.aselectAllStudents();
	}

	/*
	 * 학생 한명 조회
	 */
	public Student getStudentById(Integer id) {
		return studentMapper.aselectStudentById(id);
	}

	/*
	 * 학생 수정 (파일 없음)
	 */
	public void modifyStudent(Student student) {
		studentMapper.aupdateStudent(student);
	}

	/*
	 * 학생 삭제
	 */
	public void removeStudent(Integer id) {
		studentMapper.adeleteStudent(id);
	}

	/*
	 * 학생 추가 + 파일 업로드
	 */
	public void addStudentWithFile(Student student, MultipartFile file) throws Exception {
		if (file != null && !file.isEmpty()) {
			saveFileToStudent(student, file);
		}
		studentMapper.ainsertStuent(student);
	}

	/*
	 * 학생 수정 + 파일 업로드
	 */
	public void modifyStudentWithFile(Student student, MultipartFile file) throws Exception {
		if (file != null && !file.isEmpty()) {
			saveFileToStudent(student, file);
			studentMapper.aupdateStudentWithFile(student);
		} else {
			studentMapper.aupdateStudent(student);
		}
	}

	private void saveFileToStudent(Student student, MultipartFile file) throws Exception {
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		String originalName = file.getOriginalFilename();
		String uniqueName = UUID.randomUUID() + "_" + originalName;

		Path savePath = Paths.get(uploadPath, uniqueName);
		file.transferTo(savePath.toFile());

		student.setFilePath(savePath.toString());
		student.setFileName(originalName);
		student.setFileSize(file.getSize());
	}
}