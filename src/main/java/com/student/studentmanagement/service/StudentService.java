package com.student.studentmanagement.service;

import java.rmi.StubNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.student.studentmanagement.dto.CourseStudentCountDTO;
import com.student.studentmanagement.dto.DepartmentStudentCountDTO;
import com.student.studentmanagement.exception.StudentNotFoundException;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.StudentRepository;

@Service
public class StudentService 
{
	@Autowired
	private StudentRepository studentRepository;
	
	public Student saveStudent(Student student)
	{
		return studentRepository.save(student);
	}
	
	public List<Student> getAllStudents()
	{
		return studentRepository.findAll();
	}
	
	public Student getStudentById(Long id)
	{
		return studentRepository.findById(id).orElseThrow(
				() -> new StudentNotFoundException(id)
				);
	}
	
	public Student updateStudent(Long id , Student student)
	{
		Student existingStudent = studentRepository.findById(id).orElseThrow(
				() -> new StudentNotFoundException(id) 
				);
		
		existingStudent.setName(student.getName());
		existingStudent.setEmail(student.getEmail());
	    existingStudent.setPhone(student.getPhone());
	    existingStudent.setAddress(student.getAddress());
	    
	    existingStudent.setCourses(student.getCourses());
	    
	    return studentRepository.save(existingStudent);
	}
		
	public void deleteStudent(Long id)
	{
		//we aren't returning anything here thats why we are not using return
		Student existingStudent =
	            studentRepository.findById(id)
	                    .orElseThrow(
	                    		() -> new StudentNotFoundException(id)
	                    		);

	    studentRepository.delete(existingStudent);
	}
	
	public Page<Student> getAllStudents(Pageable pageable)
	{
		return studentRepository.findAll(pageable); 
	}
	
	public List<DepartmentStudentCountDTO> getStudentCountByDepartment()
	{
		List<Object[]> results = studentRepository.countStudentsByDepartment();

	    List<DepartmentStudentCountDTO> departmentCounts = new ArrayList<>();

	    for (Object[] row : results)
	    {
	        String departmentName = (String) row[0];
	        long totalStudents = ((Number) row[1]).longValue();

	        departmentCounts.add(
	                new DepartmentStudentCountDTO(departmentName, totalStudents)
	        );
	    }

	    return departmentCounts;
	}
	

	public List<CourseStudentCountDTO> getStudentCountByCourse()
	{
		List<Object[]> results = studentRepository.countStudentsByCourse();

		List<CourseStudentCountDTO> courseCounts = new ArrayList<>();

		for (Object[] row : results)
		{
			String courseName = (String) row[0];
			long totalStudents = ((Number) row[1]).longValue();

			courseCounts.add(
					new CourseStudentCountDTO(courseName, totalStudents)
					);
		}

		return courseCounts;
	}

	
	public List<Student> searchStudentsByName(String name)
	{
		return studentRepository.findByNameContainingIgnoreCase(name);
	}
}
