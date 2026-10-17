package com.student.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.studentmanagement.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>
{

}
