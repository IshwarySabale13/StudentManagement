
package com.student.studentmanagement.dto;

public class CourseStudentCountDTO
{
    private String courseName;
    private long totalStudents;

    public CourseStudentCountDTO(String courseName, long totalStudents)
    {
        this.courseName = courseName;
        this.totalStudents = totalStudents;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public long getTotalStudents()
    {
        return totalStudents;
    }
}
