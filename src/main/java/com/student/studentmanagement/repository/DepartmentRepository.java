package com.student.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.studentmanagement.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>
{

}
