
package com.student.studentmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.student.studentmanagement.exception.CourseNotFoundException;
import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.CourseRepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest
{
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void testSaveCourse() {
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setCredits(4);

        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.saveCourse(course);

        assertNotNull(result);
        assertEquals("Java", result.getName());
        verify(courseRepository).save(course);
    }

    @Test
    void testGetAllCourses()
    {
        List<Course> courses = List.of(
                new Course(),
                new Course()
        );

        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourses();

        assertEquals(2, result.size());
        verify(courseRepository).findAll();
    }

    @Test
    void testGetCourseById()
    {
        Long id = 1L;
        Course course = new Course();
        course.setName("Java");

        when(courseRepository.findById(id))
                .thenReturn(Optional.of(course));

        Course result = courseService.getCourseById(id);

        assertNotNull(result);
        assertEquals("Java", result.getName());
        verify(courseRepository).findById(id);
    }

    @Test
    void testGetCourseByIdNotFound()
    {
        Long id = 1L;

        when(courseRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                CourseNotFoundException.class,
                () -> courseService.getCourseById(id)
        );

        verify(courseRepository).findById(id);
    }

    @Test
    void testUpdateCourse()
    {
        Long id = 1L;

        Course existingCourse = new Course();
        existingCourse.setName("C");
        existingCourse.setDescription("Old Description");
        existingCourse.setCredits(2);

        Course updatedCourse = new Course();
        updatedCourse.setName("Java");
        updatedCourse.setDescription("New Description");
        updatedCourse.setCredits(4);

        when(courseRepository.findById(id))
                .thenReturn(Optional.of(existingCourse));

        when(courseRepository.save(existingCourse))
                .thenReturn(existingCourse);

        Course result = courseService.updateCourse(id, updatedCourse);

        assertEquals("Java", result.getName());
        assertEquals("New Description", result.getDescription());
        assertEquals(4, result.getCredits());

        verify(courseRepository).findById(id);
        verify(courseRepository).save(existingCourse);
    }

    @Test
    void testUpdateCourseNotFound()
    {
        Long id = 1L;
        Course course = new Course();

        when(courseRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                CourseNotFoundException.class,
                () -> courseService.updateCourse(id, course)
        );

        verify(courseRepository).findById(id);
    }

    @Test
    void testDeleteCourse() {
        Long id = 1L;
        Course course = new Course();

        when(courseRepository.findById(id))
                .thenReturn(Optional.of(course));

        courseService.deleteCourse(id);

        verify(courseRepository).findById(id);
        verify(courseRepository).delete(course);
    }

    @Test
    void testDeleteCourseNotFound()
    {
        Long id = 1L;

        when(courseRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                CourseNotFoundException.class,
                () -> courseService.deleteCourse(id)
        );

        verify(courseRepository).findById(id);
    }

    @Test
    void testGetStudentsByCourseId()
    {
        Long id = 1L;

        Course course = new Course();
        List<Student> students = List.of(new Student());
        course.setStudents(students);

        when(courseRepository.findById(id))
                .thenReturn(Optional.of(course));

        List<Student> result = courseService.getStudentsByCourseId(id);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(students, result);

        verify(courseRepository).findById(id);
    }
}
