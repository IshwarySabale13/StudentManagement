package com.student.studentmanagement.dto;

public class DepartmentStudentCountDTO 
{
	private String departmentName;
	private long totalStudents;
	
	public DepartmentStudentCountDTO(String departmentName, long totalStudents)
    {
        this.departmentName = departmentName;
        this.totalStudents = totalStudents;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public long getTotalStudents()
    {
        return totalStudents;
    }
}
