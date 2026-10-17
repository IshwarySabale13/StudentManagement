package com.student.studentmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.student.studentmanagement.model.Student;

public interface StudentRepository extends JpaRepository<Student , Long> 
{
	List<Student> findByNameContainingIgnoreCase(String name);
	
	Page<Student> findAll(Pageable pageable);
	
	
	@Query("SELECT d.name, COUNT(s) FROM Student s JOIN s.department d GROUP BY d.name")
	List<Object[]> countStudentsByDepartment();
}
