package com.student.studentmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    @PostMapping
    public Course saveCourse(@RequestBody Course course)
    {
        return courseService.saveCourse(course);
    }

    @GetMapping
    public List<Course> getAllCourses()
    {
        return courseService.getAllCourses();
    }
    
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id)
    {
    	return courseService.getCourseById(id);	
    }
    
    @GetMapping("{id}/students")
    public List<Student> getStudentByCourseId(@PathVariable Long id)
    {
    	return courseService.getStudentsByCourseId(id);
    }
    
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id , @RequestBody Course course)
    {
    	return courseService.updateCourse(id, course); 
    }
    
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id)
    {
    	courseService.deleteCourse(id);
    }
 
}