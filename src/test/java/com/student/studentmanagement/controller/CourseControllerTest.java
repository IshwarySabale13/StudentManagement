
package com.student.studentmanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.service.CourseService;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest
{
    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    // 1. Test saveCourse()
    @Test
    void testSaveCourse() {

        Course course = new Course();

        when(courseService.saveCourse(course)).thenReturn(course);

        Course result = courseController.saveCourse(course);

        assertSame(course, result);

        verify(courseService).saveCourse(course);
    }

    // 2. Test getAllCourses()
    @Test
    void testGetAllCourses()
    {
        List<Course> courses = List.of(new Course(), new Course());

        when(courseService.getAllCourses()).thenReturn(courses);

        List<Course> result = courseController.getAllCourses();

        assertEquals(2, result.size());

        verify(courseService).getAllCourses();
    }

    // 3. Test getCourseById()
    @Test
    void testGetCourseById()
    {
        Long id = 1L;

        Course course = new Course();

        when(courseService.getCourseById(id)).thenReturn(course);

        Course result = courseController.getCourseById(id);

        assertSame(course, result);

        verify(courseService).getCourseById(id);
    }

    // 4. Test getStudentByCourseId()
    @Test
    void testGetStudentByCourseId()
    {
        Long id = 1L;

        List<Student> students = List.of(new Student(), new Student());

        when(courseService.getStudentsByCourseId(id))
                .thenReturn(students);

        List<Student> result = courseController.getStudentByCourseId(id);

        assertEquals(2, result.size());

        verify(courseService).getStudentsByCourseId(id);
    }

    // 5. Test updateCourse()
    @Test
    void testUpdateCourse()
    {
        Long id = 1L;

        Course course = new Course();

        when(courseService.updateCourse(id, course)).thenReturn(course);

        Course result = courseController.updateCourse(id, course);

        assertSame(course, result);

        verify(courseService).updateCourse(id, course);
    }

    // 6. Test deleteCourse()
    @Test
    void testDeleteCourse()
    {
        Long id = 1L;

        courseController.deleteCourse(id);

        verify(courseService).deleteCourse(id);
    }
}
