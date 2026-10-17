package com.student.studentmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.studentmanagement.dto.DashboardDTO;
import com.student.studentmanagement.repository.CourseRepository;
import com.student.studentmanagement.repository.DepartmentRepository;
import com.student.studentmanagement.repository.StudentRepository;

@RestController
@RequestMapping("/dashboard")
public class DashboardController 
{
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping
	public DashboardDTO getDashboard()
	{
	    long totalStudents = studentRepository.count();
	    long totalDepartments = departmentRepository.count();
	    long totalCourses = courseRepository.count();

	    return new DashboardDTO(
	            totalStudents,
	            totalDepartments,
	            totalCourses
	    );
	}
}
