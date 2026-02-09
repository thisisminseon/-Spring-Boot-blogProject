package com.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.blog.domain.Student;

/*
 * @Mapper : StuentMapper 인터페이스는 /resources/mapper 와 연동하는 인테페이스라고 스프링에 알림
 * Mybatis가 이 인터페이스를 보고 자동으로 구현체를 생성한다
 * XML 파일의 SQL과 메서드가 자동으로 연결된다 
 */
@Mapper
public interface StudentMapper {

	/*
	 * 학생정보 추가(Insert)
	 */
	void insertStuent(Student student);
	
	@Insert("""
		insert into student 
		(name, age, major, file_path, file_name, file_size)
		values 
		(#{name}, #{age}, #{major}, #{filePath}, #{fileName}, #{fileSize})
	""")
	@Options(useGeneratedKeys=true, keyProperty="id")
	void ainsertStuent(Student student);
	
	/*
	 * 모든 학생 조회
	 */
	List<Student> selectAllStudents();
	
	@Select("select * from student order by id asc")
	List<Student> aselectAllStudents();
	
	/*
	 * id로 학생조회
	 */
	Student selectStudentById(Integer id);
	
	@Select("select * from student where id = #{id}")
	Student aselectStudentById(@Param("id") Integer id);
	
	/*
	 * 학생 정보 수정
	 */
	void updateStudent(Student student);
	
	@Update("""
		update student 
		set name=#{name}, age=#{age}, major=#{major}
		where id=#{id}
	""")
	void aupdateStudent(Student student);
	
	@Update("""
		update student 
		set 
			name=#{name},
			age=#{age},
			major=#{major},
			file_path=#{filePath},
			file_name=#{fileName},
			file_size=#{fileSize}
		where id=#{id}
	""")
	void aupdateStudentWithFile(Student student);
	
	/*
	 * 학생 삭제
	 */
	void deleteStudent(Integer id);
	
	@Delete("delete from student where id=#{id}")
	void adeleteStudent(@Param("id") Integer id);
}