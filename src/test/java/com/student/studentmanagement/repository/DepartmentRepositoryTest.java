package com.student.studentmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.student.studentmanagement.model.Department;

@DataJpaTest
class DepartmentRepositoryTest
{
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Test
    void saveDepartment_shouldSaveDepartmentSuccessfully()
    {
        Department department = new Department();
        department.setName("Computer Science");
        department.setDescription("Computer Department");

        Department savedDepartment = departmentRepository.save(department);

        assertNotNull(savedDepartment.getId());
        assertEquals("Computer Science", savedDepartment.getName());
        assertEquals("Computer Department", savedDepartment.getDescription());
    }
    
    @Test
    void findAll_shouldReturnAllDepartments()
    {
        Department department1 = new Department();
        department1.setName("Computer Science");
        department1.setDescription("Computer Department");

        Department department2 = new Department();
        department2.setName("Commerce");
        department2.setDescription("Commerce Department");

        departmentRepository.save(department1);
        departmentRepository.save(department2);

        List<Department> departments = departmentRepository.findAll();

        assertEquals(2, departments.size());
    }
    
    @Test
    void findById_shouldReturnDepartmentSuccessfully()
    {
        Department department = new Department();
        department.setName("Science");
        department.setDescription("Science Department");

        Department savedDepartment = departmentRepository.save(department);

        Department foundDepartment = departmentRepository
                .findById(savedDepartment.getId())
                .orElse(null);

        assertNotNull(foundDepartment);
        assertEquals("Science", foundDepartment.getName());
    }
    
    @Test
    void findById_shouldReturnEmptyWhenDepartmentDoesNotExist()
    {
        var result = departmentRepository.findById(999L);

        assertTrue(result.isEmpty());
    }
    
    @Test
    void deleteById_shouldDeleteDepartmentSuccessfully()
    {
        Department department = new Department();
        department.setName("Arts");
        department.setDescription("Arts Department");

        Department savedDepartment = departmentRepository.save(department);

        Long departmentId = savedDepartment.getId();

        departmentRepository.deleteById(departmentId);

        assertTrue(departmentRepository.findById(departmentId).isEmpty());
    }
    
    @Test
    void updateDepartment_shouldUpdateDepartmentSuccessfully() 
    {
        Department department = new Department();
        department.setName("Commerce");
        department.setDescription("Old Description");

        Department savedDepartment = departmentRepository.save(department);

        savedDepartment.setName("Advanced Commerce");
        savedDepartment.setDescription("Updated Description");

        Department updatedDepartment = departmentRepository.save(savedDepartment);

        assertEquals("Advanced Commerce", updatedDepartment.getName());
        assertEquals("Updated Description", updatedDepartment.getDescription());
    }
    
    @Test
    void count_shouldReturnTotalDepartmentCount()
    {
        Department department1 = new Department();
        department1.setName("Computer Science");
        department1.setDescription("Computer Department");

        Department department2 = new Department();
        department2.setName("Commerce");
        department2.setDescription("Commerce Department");

        Department department3 = new Department();
        department3.setName("Arts");
        department3.setDescription("Arts Department");

        departmentRepository.save(department1);
        departmentRepository.save(department2);
        departmentRepository.save(department3);

        long count = departmentRepository.count();

        assertEquals(3, count);
    }

}