package com.student.studentmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;

@DataJpaTest
class CourseRepositoryTest
{
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;
    
    @Test
    void saveCourse_shouldSaveCourseSuccessfully()
    {
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setCredits(4);

        Course savedCourse = courseRepository.save(course);

        assertNotNull(savedCourse.getId());
        assertEquals("Java", savedCourse.getName());
        assertEquals(4, savedCourse.getCredits());
    }
    
    @Test
    void findAll_shouldReturnAllCourses()
    {
        Course course1 = new Course();
        course1.setName("Java");
        course1.setDescription("Java Programming");
        course1.setCredits(4);

        Course course2 = new Course();
        course2.setName("Python");
        course2.setDescription("Python Programming");
        course2.setCredits(3);

        courseRepository.save(course1);
        courseRepository.save(course2);

        List<Course> courses = courseRepository.findAll();

        assertEquals(2, courses.size());
    }
    
    @Test
    void findById_shouldReturnCourseSuccessfully()
    {
        Course course = new Course();
        course.setName("Spring Boot");
        course.setDescription("Spring Boot Development");
        course.setCredits(5);

        Course savedCourse = courseRepository.save(course);

        Course foundCourse = courseRepository
                .findById(savedCourse.getId())
                .orElse(null);

        assertNotNull(foundCourse);
        assertEquals("Spring Boot", foundCourse.getName());
    }
    
    @Test
    void deleteById_shouldDeleteCourseSuccessfully()
    {
        Course course = new Course();
        course.setName("C++");
        course.setDescription("C++ Programming");
        course.setCredits(3);

        Course savedCourse = courseRepository.save(course);

        Long courseId = savedCourse.getId();

        courseRepository.deleteById(courseId);

        assertTrue(courseRepository.findById(courseId).isEmpty());
    }
    
    @Test
    void findCourseNamesByStudentCount_shouldReturnCoursesInStudentCountOrder() 
    {
        Course java = new Course();
        java.setName("Java");
        java.setDescription("Java Programming");
        java.setCredits(4);

        Course python = new Course();
        python.setName("Python");
        python.setDescription("Python Programming");
        python.setCredits(3);

        Course savedJava = courseRepository.save(java);
        Course savedPython = courseRepository.save(python);

        Student student1 = new Student();
        student1.setName("Rahul");
        student1.setEmail("rahul@gmail.com");
        student1.setPhone("9876543210");
        student1.setCourses(List.of(savedJava));

        Student student2 = new Student();
        student2.setName("Priya");
        student2.setEmail("priya@gmail.com");
        student2.setPhone("9876543211");
        student2.setCourses(List.of(savedJava));

        Student student3 = new Student();
        student3.setName("Amit");
        student3.setEmail("amit@gmail.com");
        student3.setPhone("9876543212");
        student3.setCourses(List.of(savedPython));

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        List<String> result =
                courseRepository.findCourseNamesByStudentCount();

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0));
        assertEquals("Python", result.get(1));
    }

}