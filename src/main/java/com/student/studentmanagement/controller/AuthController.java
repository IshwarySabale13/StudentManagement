
package com.student.studentmanagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.student.studentmanagement.dto.AuthResponse;
import com.student.studentmanagement.dto.LoginRequest;
import com.student.studentmanagement.dto.RegisterRequest;
import com.student.studentmanagement.model.User;
import com.student.studentmanagement.security.JwtService;
import com.student.studentmanagement.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/register")
	public Map<String, String> registerUser(
	        @Valid @RequestBody RegisterRequest request)
	{

	    User user = new User();

	    user.setUsername(request.getUsername());
	    user.setEmail(request.getEmail());
	    user.setPassword(request.getPassword());
	    user.setRole("USER");

	    // Public registration always creates a USER
	    user.setRole("USER");

	    try
	    {
	        userService.registerUser(user);

	        return Map.of(
	            "message", "User registered successfully"
	        );

	    } 
	    catch (IllegalArgumentException exception)
	    {

	        throw new ResponseStatusException(
	            HttpStatus.BAD_REQUEST,
	            exception.getMessage()
	        );
	    }
	}

	@PostMapping("/login")
	public AuthResponse loginUser(@Valid @RequestBody LoginRequest request) {
		try {
			User user = userService.loginUser(request.getUsername(), request.getPassword());

			String token = jwtService.generateToken(user.getUsername());

			return new AuthResponse(token, "Login successful");

		} catch (IllegalArgumentException exception) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage());
		}
	}

}
