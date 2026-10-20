package com.student.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.student.studentmanagement.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>
{
	@Query("SELECT c.name FROM Course c LEFT JOIN c.students s GROUP BY c.name ORDER BY COUNT(s) DESC")
    java.util.List<String> findCourseNamesByStudentCount();
}
