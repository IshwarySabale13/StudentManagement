package com.student.studentmanagement.dto;

public class DashboardDTO 
{
	private long totalStudents;
	private long totalDepartments;
	private long totalCourses;
	
	public DashboardDTO(long totalStudents , long totalDepartments , long totalCourses)
	{
		this.totalStudents = totalStudents;
		this.totalDepartments = totalDepartments;
		this.totalCourses = totalCourses;
		
	}	
		
		public long getTotalStudents()
	    {
	        return totalStudents;
	    }

	    public long getTotalDepartments()
	    {
	        return totalDepartments;
	    }

	    public long getTotalCourses()
	    {
	        return totalCourses;
	    }
}
