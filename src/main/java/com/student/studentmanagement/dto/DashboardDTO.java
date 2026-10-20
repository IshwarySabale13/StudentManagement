
package com.student.studentmanagement.dto;

public class DashboardDTO
{
    private long totalStudents;
    private long totalDepartments;
    private long totalCourses;
    private String mostPopularCourse;
    private long mostPopularCourseStudents;

    public DashboardDTO(
            long totalStudents,
            long totalDepartments,
            long totalCourses,
            String mostPopularCourse,
            long mostPopularCourseStudents)
    {
        this.totalStudents = totalStudents;
        this.totalDepartments = totalDepartments;
        this.totalCourses = totalCourses;
        this.mostPopularCourse = mostPopularCourse;
        this.mostPopularCourseStudents = mostPopularCourseStudents;
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

    public String getMostPopularCourse()
    {
        return mostPopularCourse;
    }

    public long getMostPopularCourseStudents()
    {
        return mostPopularCourseStudents;
    }
}
