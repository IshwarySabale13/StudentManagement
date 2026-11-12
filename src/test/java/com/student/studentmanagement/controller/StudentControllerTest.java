
package com.student.studentmanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.student.studentmanagement.dto.CourseStudentCountDTO;
import com.student.studentmanagement.dto.DepartmentStudentCountDTO;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.service.StudentService;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest
{
    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    // 1. Test saveStudent()
    @Test
    void testSaveStudent()
    {
        Student student = new Student();

        when(studentService.saveStudent(student)).thenReturn(student);

        Student result = studentController.saveStudent(student);

        assertSame(student, result);

        verify(studentService).saveStudent(student);
    }

    // 2. Test getAllStudents()
    @Test
    void testGetAllStudents()
    {
        List<Student> students = List.of(new Student(), new Student());

        when(studentService.getAllStudents()).thenReturn(students);

        List<Student> result = studentController.getAllStudents();

        assertEquals(2, result.size());

        verify(studentService).getAllStudents();
    }

    // 3. Test searchStudentsByName()
    @Test
    void testSearchStudentsByName()
    {
        String name = "Rahul";

        List<Student> students = List.of(new Student());

        when(studentService.searchStudentsByName(name))
                .thenReturn(students);

        List<Student> result =
                studentController.searchStudentsByName(name);

        assertEquals(1, result.size());

        verify(studentService).searchStudentsByName(name);
    }

    // 4. Test getStudentById()
    @Test
    void testGetStudentById()
    {
        Long id = 1L;

        Student student = new Student();

        when(studentService.getStudentById(id)).thenReturn(student);

        Student result = studentController.getStudentById(id);

        assertSame(student, result);

        verify(studentService).getStudentById(id);
    }

    // 5. Test updateStudent()
    @Test
    void testUpdateStudent() {

        Long id = 1L;

        Student student = new Student();

        when(studentService.updateStudent(id, student))
                .thenReturn(student);

        Student result = studentController.updateStudent(id, student);

        assertSame(student, result);

        verify(studentService).updateStudent(id, student);
    }

    // 6. Test assignCourseToStudent()
    @Test
    void testAssignCourseToStudent()
    {
        Long studentId = 1L;
        Long courseId = 2L;

        Student student = new Student();

        when(studentService.assignCourseToStudent(studentId, courseId))
                .thenReturn(student);

        Student result =
                studentController.assignCourseToStudent(studentId, courseId);

        assertSame(student, result);

        verify(studentService).assignCourseToStudent(studentId, courseId);
    }

    // 7. Test removeCourseFromStudent()
    @Test
    void testRemoveCourseFromStudent()
    {
        Long studentId = 1L;
        Long courseId = 2L;

        Student student = new Student();

        when(studentService.removeCourseFromStudent(studentId, courseId))
                .thenReturn(student);

        Student result =
                studentController.removeCourseFromStudent(studentId, courseId);

        assertSame(student, result);

        verify(studentService).removeCourseFromStudent(studentId, courseId);
    }

    // 8. Test deleteStudent()
    @Test
    void testDeleteStudent()
    {
        Long id = 1L;

        studentController.deleteStudent(id);

        verify(studentService).deleteStudent(id);
    }

    // 9. Test getStudents() with ascending sorting
    @Test
    void testGetStudentsWithAscendingSorting()
    {
        Page<Student> expectedPage = Page.empty();

        when(studentService.getAllStudents(any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<Student> result =
                studentController.getStudents(0, 5, "name", "asc");

        assertSame(expectedPage, result);

        verify(studentService).getAllStudents(argThat(pageable ->
                pageable.getPageNumber() == 0
                && pageable.getPageSize() == 5
                && pageable.getSort().equals(Sort.by("name").ascending())
        ));
    }

    // 10. Test getStudents() with descending sorting
    @Test
    void testGetStudentsWithDescendingSorting()
    {
        Page<Student> expectedPage = Page.empty();

        when(studentService.getAllStudents(any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<Student> result =
                studentController.getStudents(1, 10, "name", "desc");

        assertSame(expectedPage, result);

        verify(studentService).getAllStudents(argThat(pageable ->
                pageable.getPageNumber() == 1
                && pageable.getPageSize() == 10
                && pageable.getSort().equals(Sort.by("name").descending())
        ));
    }

    // 11. Test getStudents() with multiple sorting fields
    @Test
    void testGetStudentsWithMultipleSortingFields()
    {
        Page<Student> expectedPage = Page.empty();

        when(studentService.getAllStudents(any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<Student> result =
                studentController.getStudents(0, 5, "name,email", "asc");

        assertSame(expectedPage, result);

        Sort expectedSort = Sort.by("name").ascending()
                .and(Sort.by("email").ascending());

        verify(studentService).getAllStudents(argThat(pageable ->
                pageable.getSort().equals(expectedSort)
        ));
    }

    // 12. Test negative page number
    @Test
    void testGetStudentsWithNegativePage()
    {
        assertThrows(IllegalArgumentException.class, () ->
                studentController.getStudents(-1, 5, "id", "asc")
        );

        verifyNoInteractions(studentService);
    }

    // 13. Test page size less than 1
    @Test
    void testGetStudentsWithInvalidPageSize()
    {
        assertThrows(IllegalArgumentException.class, () ->
                studentController.getStudents(0, 0, "id", "asc")
        );

        verifyNoInteractions(studentService);
    }

    // 14. Test page size greater than 100
    @Test
    void testGetStudentsWithPageSizeAbove100()
    {
        assertThrows(IllegalArgumentException.class, () ->
                studentController.getStudents(0, 101, "id", "asc")
        );

        verifyNoInteractions(studentService);
    }

    // 15. Test invalid sorting direction
    @Test
    void testGetStudentsWithInvalidDirection()
    {
        assertThrows(IllegalArgumentException.class, () ->
                studentController.getStudents(0, 5, "id", "random")
        );

        verifyNoInteractions(studentService);
    }

    // 16. Test invalid sorting field
    @Test
    void testGetStudentsWithInvalidSortField()
    {
        assertThrows(IllegalArgumentException.class, () ->
                studentController.getStudents(0, 5, "password", "asc")
        );

        verifyNoInteractions(studentService);
    }

    // 17. Test sorting direction is case-insensitive
    @Test
    void testGetStudentsWithUppercaseDirection()
    {
        Page<Student> expectedPage = Page.empty();

        when(studentService.getAllStudents(any(Pageable.class)))
                .thenReturn(expectedPage);

        Page<Student> result =
                studentController.getStudents(0, 5, "id", "DESC");

        assertSame(expectedPage, result);

        verify(studentService).getAllStudents(argThat(pageable ->
                pageable.getSort().equals(Sort.by("id").descending())
        ));
    }

    // 18. Test getStudentCountByDepartment()
    @Test
    void testGetStudentCountByDepartment()
    {
        List<DepartmentStudentCountDTO> counts = List.of();

        when(studentService.getStudentCountByDepartment())
                .thenReturn(counts);

        List<DepartmentStudentCountDTO> result =
                studentController.getStudentCountByDepartment();

        assertSame(counts, result);

        verify(studentService).getStudentCountByDepartment();
    }

    // 19. Test getStudentCountByCourse()
    @Test
    void testGetStudentCountByCourse()
    {
        List<CourseStudentCountDTO> counts = List.of();

        when(studentService.getStudentCountByCourse())
                .thenReturn(counts);

        List<CourseStudentCountDTO> result =
                studentController.getStudentCountByCourse();

        assertSame(counts, result);

        verify(studentService).getStudentCountByCourse();
    }
}
