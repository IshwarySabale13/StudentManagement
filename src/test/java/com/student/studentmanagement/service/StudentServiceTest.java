package com.student.studentmanagement.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.student.studentmanagement.model.Course;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.StudentRepository;
import com.student.studentmanagement.repository.CourseRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import com.student.studentmanagement.dto.CourseStudentCountDTO;
import com.student.studentmanagement.dto.DepartmentStudentCountDTO;
import com.student.studentmanagement.exception.CourseNotFoundException;
import com.student.studentmanagement.exception.StudentNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest 
{
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void saveStudent_shouldReturnSavedStudent() 
    {
        Student inputStudent = new Student();
        Student savedStudent = new Student();

        when(studentRepository.save(inputStudent))
                .thenReturn(savedStudent);

        Student result = studentService.saveStudent(inputStudent);

        assertSame(savedStudent, result);

        verify(studentRepository).save(inputStudent);
    }
    
    @Test
    void getStudentById_whenStudentExists_returnsStudent()
    {
        Student student = new Student();

        when(studentRepository.findById(1L))
                .thenReturn(java.util.Optional.of(student));

        Student result = studentService.getStudentById(1L);

        assertSame(student, result);

        verify(studentRepository).findById(1L);
    }
    
    @Test
    void getStudentById_whenStudentDoesNotExist_throwsException() 
    {
        when(studentRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                StudentNotFoundException.class,
                () -> studentService.getStudentById(999L)
        );

        verify(studentRepository).findById(999L);
    }
    
    @Test
    void updateStudent_whenStudentExists_returnsUpdatedStudent()
    {
        Student existingStudent = new Student();
        existingStudent.setName("Amit");
        existingStudent.setEmail("amit@example.com");

        Student updatedDetails = new Student();
        updatedDetails.setName("Amit Updated");
        updatedDetails.setEmail("amit.updated@example.com");
        updatedDetails.setPhone("9876543210");
        updatedDetails.setAddress("Mumbai");

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(existingStudent));

        when(studentRepository.save(existingStudent))
                .thenReturn(existingStudent);

        Student result = studentService.updateStudent(1L, updatedDetails);

        assertEquals("Amit Updated", result.getName());
        assertEquals("amit.updated@example.com", result.getEmail());
        assertEquals("9876543210", result.getPhone());
        assertEquals("Mumbai", result.getAddress());

        verify(studentRepository).save(existingStudent);
    }
    
    @Test
    void deleteStudent_whenStudentExists_deletesStudent()
    {
        Student existingStudent = new Student();
        existingStudent.setName("Amit");

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(existingStudent));

        studentService.deleteStudent(1L);

        verify(studentRepository).findById(1L);

        verify(studentRepository).delete(existingStudent);
    }
    
    @Test
    void assignCourseToStudent_whenStudentAndCourseExist_assignsCourse()
    {
        Student student = new Student();
        student.setCourses(new ArrayList<>());

        Course course = new Course();
        course.setId(2L);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(2L))
                .thenReturn(Optional.of(course));

        when(studentRepository.save(student))
                .thenReturn(student);

        Student result = studentService.assignCourseToStudent(1L, 2L);

        assertEquals(1, result.getCourses().size());

        assertSame(course, result.getCourses().get(0));

        verify(studentRepository).save(student);
    }
    
    @Test
    void assignCourseToStudent_whenCourseAlreadyAssigned_doesNotDuplicate() 
    {
        Student student = new Student();

        Course existingCourse = new Course();
        existingCourse.setId(2L);

        List<Course> courses = new ArrayList<>();
        courses.add(existingCourse);

        student.setCourses(courses);

        Course course = new Course();
        course.setId(2L);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(2L))
                .thenReturn(Optional.of(course));

        when(studentRepository.save(student))
                .thenReturn(student);

        Student result = studentService.assignCourseToStudent(1L, 2L);

        assertEquals(1, result.getCourses().size());

        verify(studentRepository).save(student);
    }
    
    @Test
    void removeCourseFromStudent_whenCourseExists_removesCourse() 
    {
        Student student = new Student();

        Course course = new Course();
        course.setId(2L);

        List<Course> courses = new ArrayList<>();
        courses.add(course);

        student.setCourses(courses);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(2L))
                .thenReturn(Optional.of(course));

        when(studentRepository.save(student))
                .thenReturn(student);

        Student result =
                studentService.removeCourseFromStudent(1L, 2L);

        assertEquals(0, result.getCourses().size());

        verify(studentRepository).save(student);
    }
    
    @Test
    void assignCourseToStudent_whenCourseDoesNotExist_throwsException() 
    {
        Student student = new Student();

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                CourseNotFoundException.class,
                () -> studentService.assignCourseToStudent(1L, 999L)
        );

        verify(courseRepository).findById(999L);
    }
    
    @Test
    void assignCourseToStudent_whenStudentDoesNotExist_throwsException()
    {
        when(studentRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                StudentNotFoundException.class,
                () -> studentService.assignCourseToStudent(999L, 2L)
        );

        verify(studentRepository).findById(999L);
    }
    
    @Test
    void searchStudentsByName_returnsMatchingStudents() 
    {
        Student student = new Student();
        student.setName("Amit");

        List<Student> expectedStudents = List.of(student);

        when(studentRepository.findByNameContainingIgnoreCase("amit"))
                .thenReturn(expectedStudents);

        List<Student> result =
                studentService.searchStudentsByName("amit");

        assertEquals(1, result.size());

        assertSame(student, result.get(0));

        verify(studentRepository)
                .findByNameContainingIgnoreCase("amit");
    }
    
    @Test
    void getAllStudents_returnsAllStudents() 
    {
        Student student1 = new Student();
        student1.setName("Amit");

        Student student2 = new Student();
        student2.setName("Rahul");

        List<Student> expectedStudents = List.of(student1, student2);

        when(studentRepository.findAll())
                .thenReturn(expectedStudents);

        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());

        assertSame(student1, result.get(0));
        assertSame(student2, result.get(1));

        verify(studentRepository).findAll();
    }
    
    @Test
    void getAllStudents_withPagination_returnsStudentPage() 
    {
        Pageable pageable = PageRequest.of(0, 2);

        Student student1 = new Student();
        student1.setName("Amit");

        Student student2 = new Student();
        student2.setName("Rahul");

        List<Student> students = List.of(student1, student2);

        Page<Student> expectedPage =
                new PageImpl<>(students, pageable, 2);

        when(studentRepository.findAll(pageable))
                .thenReturn(expectedPage);

        Page<Student> result = studentService.getAllStudents(pageable);

        assertEquals(2, result.getContent().size());
        assertSame(expectedPage, result);

        verify(studentRepository).findAll(pageable);
    }
    
    @Test
    void getStudentCountByDepartment_returnsDepartmentCounts()
    {
        List<Object[]> queryResults = new ArrayList<>();

        queryResults.add(new Object[]{"Computer Science", 5L});
        queryResults.add(new Object[]{"Commerce", 3L});

        when(studentRepository.countStudentsByDepartment())
                .thenReturn(queryResults);

        List<DepartmentStudentCountDTO> result =
                studentService.getStudentCountByDepartment();

        assertEquals(2, result.size());

        assertEquals("Computer Science",
                result.get(0).getDepartmentName());

        assertEquals(5L,
                result.get(0).getTotalStudents());

        assertEquals("Commerce",
                result.get(1).getDepartmentName());

        assertEquals(3L,
                result.get(1).getTotalStudents());

        verify(studentRepository).countStudentsByDepartment();
    }
    
    @Test
    void getStudentCountByCourse_returnsCourseCounts()
    {
        List<Object[]> queryResults = new ArrayList<>();

        queryResults.add(new Object[]{"Java Programming", 5L});
        queryResults.add(new Object[]{"Python Programming", 3L});

        when(studentRepository.countStudentsByCourse())
                .thenReturn(queryResults);

        List<CourseStudentCountDTO> result =
                studentService.getStudentCountByCourse();

        assertEquals(2, result.size());

        assertEquals("Java Programming",
                result.get(0).getCourseName());

        assertEquals(5L,
                result.get(0).getTotalStudents());

        assertEquals("Python Programming",
                result.get(1).getCourseName());

        assertEquals(3L,
                result.get(1).getTotalStudents());

        verify(studentRepository).countStudentsByCourse();
    }
    
    @Test
    void removeCourseFromStudent_whenCourseDoesNotExist_throwsException()
    {
        Student student = new Student();

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                CourseNotFoundException.class,
                () -> studentService.removeCourseFromStudent(1L, 999L)
        );

        verify(courseRepository).findById(999L);
    }
    
    @Test
    void removeCourseFromStudent_whenStudentDoesNotExist_throwsException()
    {
        when(studentRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                StudentNotFoundException.class,
                () -> studentService.removeCourseFromStudent(999L, 2L)
        );

        verify(studentRepository).findById(999L);
    }
    
    @Test
    void updateStudent_whenStudentDoesNotExist_throwsException()
    {
        Student updatedDetails = new Student();
        updatedDetails.setName("Amit Updated");

        when(studentRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                StudentNotFoundException.class,
                () -> studentService.updateStudent(999L, updatedDetails)
        );

        verify(studentRepository).findById(999L);
    }
    
    @Test
    void deleteStudent_whenStudentDoesNotExist_throwsException() 
    {
        when(studentRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                StudentNotFoundException.class,
                () -> studentService.deleteStudent(999L)
        );

        verify(studentRepository).findById(999L);
    }
    
    @Test
    void assignCourseToStudent_whenCoursesListIsNull_initializesList()
    {
        Student student = new Student();

        Course course = new Course();
        course.setId(2L);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(2L))
                .thenReturn(Optional.of(course));

        when(studentRepository.save(student))
                .thenReturn(student);

        Student result = studentService.assignCourseToStudent(1L, 2L);

        assertEquals(1, result.getCourses().size());

        assertSame(course, result.getCourses().get(0));

        verify(studentRepository).save(student);
    }
    
    @Test
    void removeCourseFromStudent_whenCoursesListIsNull_savesStudent()
    {
        Student student = new Student();

        Course course = new Course();
        course.setId(2L);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(2L))
                .thenReturn(Optional.of(course));

        when(studentRepository.save(student))
                .thenReturn(student);

        Student result = studentService.removeCourseFromStudent(1L, 2L);

        assertSame(student, result);

        verify(studentRepository).save(student);
    }
    
    @Test
    void removeCourseFromStudent_whenCourseIsNotAssigned_keepsExistingCourses() 
    {
        Student student = new Student();

        Course existingCourse = new Course();
        existingCourse.setId(1L);

        List<Course> courses = new ArrayList<>();
        courses.add(existingCourse);
        student.setCourses(courses);

        Course courseToRemove = new Course();
        courseToRemove.setId(2L);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(2L))
                .thenReturn(Optional.of(courseToRemove));

        when(studentRepository.save(student))
                .thenReturn(student);

        Student result = studentService.removeCourseFromStudent(1L, 2L);

        assertEquals(1, result.getCourses().size());
        assertSame(existingCourse, result.getCourses().get(0));

        verify(studentRepository).save(student);
    }
    
    
}