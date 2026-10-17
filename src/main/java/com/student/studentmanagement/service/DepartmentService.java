package com.student.studentmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.studentmanagement.exception.DepartmentNotFoundException;
import com.student.studentmanagement.model.Department;
import com.student.studentmanagement.repository.DepartmentRepository;

@Service
public class DepartmentService 
{
	@Autowired
	private DepartmentRepository departmentRepository;
	
	public Department saveDepartment(Department department)
	{
		return departmentRepository.save(department);
	}
	
	public List<Department> getAllDepartments()
	{
		return departmentRepository.findAll();
	}
	
	public Department getDepartmentById(Long id)
	{
		return departmentRepository.findById(id).orElseThrow(
				() -> new DepartmentNotFoundException(id)
				);
	}
	
	public Department updateDepartment(Long id , Department department)
	{
		Department existingDepartment = departmentRepository.findById(id).orElseThrow(
				() -> new DepartmentNotFoundException(id)
	            );

	    existingDepartment.setName(department.getName());
	    existingDepartment.setDescription(department.getDescription());

	    return departmentRepository.save(existingDepartment);
	}
	
	public void deleteDepartment(Long id)
	{
		Department existingDepartment = departmentRepository.findById(id).orElseThrow(
				() -> new DepartmentNotFoundException(id)
	            );

	    departmentRepository.delete(existingDepartment);
	}
}
