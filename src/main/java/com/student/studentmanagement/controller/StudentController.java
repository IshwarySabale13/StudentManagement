package com.student.studentmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.student.studentmanagement.dto.DepartmentStudentCountDTO;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController 
{
	@Autowired
	private StudentService studentService;
	
	@PostMapping
	public Student saveStudent(@Valid @RequestBody Student student)
	{
		return studentService.saveStudent(student);
	}
	
	@GetMapping
	public List<Student> getAllStudents()
	{
		return studentService.getAllStudents();
	}
	
	@GetMapping("/search")
	public List<Student> searchStudentsByName(@RequestParam String name)
	{
		return studentService.searchStudentsByName(name);  
	}
	
	@GetMapping("/{id}")
	public Student getStudentById (@PathVariable Long id)
	{
		return studentService.getStudentById(id);
	}
	
	@PutMapping("/{id}")
	public Student updateStudent(@PathVariable Long id , @Valid @RequestBody Student student)
	{
		return studentService.updateStudent(id, student);
	}
	
	@DeleteMapping("/{id}")
	public void deleteStudent(@PathVariable Long id)
	{
		studentService.deleteStudent(id);
	}
	//we are using fix path thats why no curly brackets  and {} means dyanamic data means can be changes as pr user request 
	@GetMapping("/page")
	public Page<Student> getStudents(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "2") int size , 
			@RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction
	        )
	{
		 if (page < 0) 
		    {
		        throw new IllegalArgumentException("Page number cannot be negative");
		    }

		    if (size < 1) 
		    {
		        throw new IllegalArgumentException("Page size must be at least 1");
		    }
		    
		    if (size > 100) 
		    {
		        throw new IllegalArgumentException("Page size cannot exceed 100");
		    }
		    
		    if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) 
		    {
		        throw new IllegalArgumentException("Sort direction must be 'asc' or 'desc'");
		    }
		    
		    List<String> allowedSortFields = List.of
		    (
		            "id", "name", "email", "phone", "address"
		    );

		    for (String field : sortBy.split(","))
		    {
		        if (!allowedSortFields.contains(field.trim())) 
		        {
		            throw new IllegalArgumentException
		            (
		                    "Invalid sort field: " + field.trim()
		            );
		        }
		    }    
		    
		Sort sort = Sort.unsorted();

	    for (String field : sortBy.split(",")) 
	    {
	        Sort fieldSort = direction.equalsIgnoreCase("desc")
	                ? Sort.by(field.trim()).descending()
	                : Sort.by(field.trim()).ascending();

	        sort = sort.and(fieldSort);
	    }
		 
	    Pageable pageable = PageRequest.of(page, size , sort);
	    
		return studentService.getAllStudents(pageable);
	}
	
	@GetMapping("/department-counts")
	public List<DepartmentStudentCountDTO> getStudentCountByDepartment()
	{
		return studentService.getStudentCountByDepartment();
	}
	
}
