
package com.student.studentmanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.student.studentmanagement.dto.DashboardDTO;
import com.student.studentmanagement.repository.CourseRepository;
import com.student.studentmanagement.repository.DepartmentRepository;
import com.student.studentmanagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest
{
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private DashboardController dashboardController;

    // Test 1: Dashboard when course data is available
    @Test
    void testGetDashboardWithCourseData() {

        when(studentRepository.count()).thenReturn(100L);
        when(departmentRepository.count()).thenReturn(5L);
        when(courseRepository.count()).thenReturn(10L);

        List<Object[]> courseCounts = Collections.singletonList(
                new Object[] {"Java", 40L}
        );

        when(studentRepository.countStudentsByCourse())
                .thenReturn(courseCounts);

        DashboardDTO result = dashboardController.getDashboard();

        assertNotNull(result);

        assertEquals(100L, result.getTotalStudents());
        assertEquals(5L, result.getTotalDepartments());
        assertEquals(10L, result.getTotalCourses());

        assertEquals("Java", result.getMostPopularCourse());
        assertEquals(40L, result.getMostPopularCourseStudents());

        verify(studentRepository).count();
        verify(departmentRepository).count();
        verify(courseRepository).count();
        verify(studentRepository).countStudentsByCourse();
    }

    // Test 2: Dashboard when no course data is available
    @Test
    void testGetDashboardWithoutCourseData()
    {
        when(studentRepository.count()).thenReturn(0L);
        when(departmentRepository.count()).thenReturn(0L);
        when(courseRepository.count()).thenReturn(0L);

        when(studentRepository.countStudentsByCourse())
                .thenReturn(Collections.emptyList());

        DashboardDTO result = dashboardController.getDashboard();

        assertNotNull(result);

        assertEquals(0L, result.getTotalStudents());
        assertEquals(0L, result.getTotalDepartments());
        assertEquals(0L, result.getTotalCourses());

        assertEquals("None", result.getMostPopularCourse());
        assertEquals(0L, result.getMostPopularCourseStudents());

        verify(studentRepository).count();
        verify(departmentRepository).count();
        verify(courseRepository).count();
        verify(studentRepository).countStudentsByCourse();
    }
}
