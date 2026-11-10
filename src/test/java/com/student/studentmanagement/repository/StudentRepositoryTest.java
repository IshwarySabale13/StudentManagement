package com.student.studentmanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.student.studentmanagement.model.Department;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.model.Course;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@DataJpaTest
class StudentRepositoryTest
{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;
    
    @Test
    void saveStudent_shouldSaveStudentSuccessfully()
    {
        Student student = new Student();

        student.setName("John");
        student.setEmail("john@gmail.com");
        student.setPhone("9876543210");
        student.setAddress("Mumbai");

        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent.getId());

        assertEquals("John", savedStudent.getName());
        assertEquals("john@gmail.com", savedStudent.getEmail());
    }
    
    @Test
    void findAll_shouldReturnAllStudents()
    {
        Student student1 = new Student();
        student1.setName("John");
        student1.setEmail("john@gmail.com");
        student1.setPhone("9876543210");
        student1.setAddress("Mumbai");

        Student student2 = new Student();
        student2.setName("Priya");
        student2.setEmail("priya@gmail.com");
        student2.setPhone("9876543211");
        student2.setAddress("Pune");

        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Student> students = studentRepository.findAll();

        assertEquals(2, students.size());
    }
    
    @Test
    void findById_shouldReturnStudentSuccessfully()
    {
        Student student = new Student();
        student.setName("Rahul");
        student.setEmail("rahul@gmail.com");
        student.setPhone("9876543210");
        student.setAddress("Mumbai");

        Student savedStudent = studentRepository.save(student);

        Student foundStudent = studentRepository
                .findById(savedStudent.getId())
                .orElse(null);

        assertNotNull(foundStudent);
        assertEquals("Rahul", foundStudent.getName());
        assertEquals("rahul@gmail.com", foundStudent.getEmail());
    }
    
    @Test
    void findById_shouldReturnEmptyWhenStudentDoesNotExist()
    {
        var result = studentRepository.findById(999L);

        assertTrue(result.isEmpty());
    }
    
    @Test
    void deleteStudent_shouldDeleteStudentSuccessfully()
    {
        Student student = new Student();
        student.setName("Amit");
        student.setEmail("amit@gmail.com");
        student.setPhone("9876543210");
        student.setAddress("Pune");

        Student savedStudent = studentRepository.save(student);

        Long studentId = savedStudent.getId();

        studentRepository.deleteById(studentId);

        assertTrue(studentRepository.findById(studentId).isEmpty());
    }
    
    @Test
    void findByNameContainingIgnoreCase_shouldReturnMatchingStudents()
    {
        Student student1 = new Student();
        student1.setName("Rahul Sharma");
        student1.setEmail("rahul@gmail.com");
        student1.setPhone("9876543210");

        Student student2 = new Student();
        student2.setName("Rahul Verma");
        student2.setEmail("rahul2@gmail.com");
        student2.setPhone("9876543211");

        Student student3 = new Student();
        student3.setName("Amit Patel");
        student3.setEmail("amit@gmail.com");
        student3.setPhone("9876543212");

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        List<Student> students =
                studentRepository.findByNameContainingIgnoreCase("rahul");

        assertEquals(2, students.size());
    }
    
    @Test
    void findAllWithPagination_shouldReturnStudentsPageByPage()
    {
        Student student1 = new Student();
        student1.setName("Rahul");
        student1.setEmail("rahul@gmail.com");
        student1.setPhone("9876543210");

        Student student2 = new Student();
        student2.setName("Priya");
        student2.setEmail("priya@gmail.com");
        student2.setPhone("9876543211");

        Student student3 = new Student();
        student3.setName("Amit");
        student3.setEmail("amit@gmail.com");
        student3.setPhone("9876543212");

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        Pageable pageable = PageRequest.of(0, 2);

        Page<Student> page = studentRepository.findAll(pageable);

        assertEquals(3, page.getTotalElements());
        assertEquals(2, page.getNumberOfElements());
        assertEquals(2, page.getTotalPages());
    }
    
    @Test
    void countStudentsByDepartment_shouldReturnCorrectCount()
    {
        Department department = new Department();
        department.setName("Computer Science");
        department.setDescription("Computer Department");

        Department savedDepartment =
                departmentRepository.save(department);

        Student student1 = new Student();
        student1.setName("Rahul");
        student1.setEmail("rahul@gmail.com");
        student1.setPhone("9876543210");
        student1.setDepartment(savedDepartment);

        Student student2 = new Student();
        student2.setName("Priya");
        student2.setEmail("priya@gmail.com");
        student2.setPhone("9876543211");
        student2.setDepartment(savedDepartment);

        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Object[]> results =
                studentRepository.countStudentsByDepartment();

        assertEquals(1, results.size());
        assertEquals("Computer Science", results.get(0)[0]);
        assertEquals(2L, ((Number) results.get(0)[1]).longValue());
    }
    
    @Test
    void countStudentsByCourse_shouldReturnCorrectCount()
    {
        Course course = new Course();
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setCredits(4);

        Course savedCourse = courseRepository.save(course);

        Student student1 = new Student();
        student1.setName("Rahul");
        student1.setEmail("rahul@gmail.com");
        student1.setPhone("9876543210");
        student1.setCourses(List.of(savedCourse));

        Student student2 = new Student();
        student2.setName("Priya");
        student2.setEmail("priya@gmail.com");
        student2.setPhone("9876543211");
        student2.setCourses(List.of(savedCourse));

        studentRepository.save(student1);
        studentRepository.save(student2);

        List<Object[]> results =
                studentRepository.countStudentsByCourse();

        assertEquals(1, results.size());
        assertEquals("Java", results.get(0)[0]);
        assertEquals(2L, ((Number) results.get(0)[1]).longValue());
    }

}