package com.student.studentmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.studentmanagement.exception.CourseNotFoundException;
import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.CourseRepository;

@Service
public class CourseService 
{
	@Autowired
	private CourseRepository courseRepository;
	
	public Course saveCourse(Course course)
	{
		return courseRepository.save(course);
	}
	
	public List<Course> getAllCourses()
	{
		return courseRepository.findAll();
	}
	
	public Course getCourseById(Long id)
	{
	    return courseRepository.findById(id).orElseThrow(
	    		() -> new CourseNotFoundException(id)
	    		);
	}
	
	public Course updateCourse(Long id , Course course)
	{
		Course existingCourse = courseRepository.findById(id).orElseThrow(
				() -> new CourseNotFoundException(id)
				);
		
		existingCourse.setName(course.getName());
	    existingCourse.setDescription(course.getDescription());
	    existingCourse.setCredits(course.getCredits());
	    
	    return courseRepository.save(existingCourse); 
	}
	
	public void deleteCourse(Long id)
	{
		Course existingCourse = courseRepository.findById(id).orElseThrow(
				() -> new CourseNotFoundException(id)
				);
				
		courseRepository.delete(existingCourse);
	}
	

	public List<Student> getStudentsByCourseId(Long id)
	{
		Course course = courseRepository.findById(id)
				.orElseThrow(
						() -> new CourseNotFoundException(id)
						);

		return course.getStudents();
	}
}
