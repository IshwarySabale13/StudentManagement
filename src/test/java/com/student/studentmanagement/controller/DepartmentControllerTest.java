
package com.student.studentmanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.student.studentmanagement.model.Department;
import com.student.studentmanagement.service.DepartmentService;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest
{
    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @Test
    void testSaveDepartment()
    {
        Department department = new Department();

        when(departmentService.saveDepartment(department))
                .thenReturn(department);

        Department result = departmentController.saveDepartment(department);

        assertNotNull(result);
        assertEquals(department, result);
        verify(departmentService).saveDepartment(department);
    }

    @Test
    void testGetAllDepartments()
    {
        List<Department> departments = List.of(
                new Department(),
                new Department()
        );

        when(departmentService.getAllDepartments())
                .thenReturn(departments);

        List<Department> result = departmentController.getAllDepartments();

        assertEquals(2, result.size());
        verify(departmentService).getAllDepartments();
    }

    @Test
    void testGetDepartmentById()
    {
        Long id = 1L;
        Department department = new Department();

        when(departmentService.getDepartmentById(id))
                .thenReturn(department);

        Department result = departmentController.getDepartmentById(id);

        assertNotNull(result);
        assertEquals(department, result);
        verify(departmentService).getDepartmentById(id);
    }

    @Test
    void testUpdateDepartment()
    {
        Long id = 1L;
        Department department = new Department();

        when(departmentService.updateDepartment(id, department))
                .thenReturn(department);

        Department result = departmentController.updateDepartment(id, department);

        assertNotNull(result);
        assertEquals(department, result);
        verify(departmentService).updateDepartment(id, department);
    }

    @Test
    void testDeleteDepartment()
    {
        Long id = 1L;

        departmentController.deleteDepartment(id);

        verify(departmentService).deleteDepartment(id);
    }
}
