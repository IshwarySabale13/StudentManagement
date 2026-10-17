package com.student.studentmanagement.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;

@Entity
public class Student 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@Email(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Phone is required")
	@Pattern(regexp = "\\d{10}", message = "Phone must contain exactly 10 digits")
	private String phone;
	private String address;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	
	@ManyToMany
	private List<Course> courses;
	
	public Student()
	{
		
	}
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getPhone() 
	{
		return phone;
	}
	
	public void setPhone(String phone) 
	{
		this.phone = phone;
	}
	
	public String getAddress() 
	{
		return address;
	}
	
	public void setAddress(String address) 
	{
		this.address = address;
	}
	
	public Department getDepartment()
	{
	    return department;
	}
	
	public void setDepartment(Department department)
	{
	    this.department = department;
	}
	
	public List<Course> getCourses()
	{
	    return courses;
	}

	public void setCourses(List<Course> courses)
	{
	    this.courses = courses;
	}
}
