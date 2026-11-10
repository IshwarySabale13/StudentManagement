package com.student.studentmanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.student.studentmanagement.dto.AuthResponse;
import com.student.studentmanagement.dto.LoginRequest;
import com.student.studentmanagement.dto.RegisterRequest;
import com.student.studentmanagement.model.User;
import com.student.studentmanagement.security.JwtService;
import com.student.studentmanagement.service.UserService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest 
{
    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;
    
    @Test
    void registerUser_whenDetailsAreValid_returnsSuccessMessage()
    {
        RegisterRequest request = new RegisterRequest();

        request.setUsername("john");
        request.setEmail("john@gmail.com");
        request.setPassword("12345");

        when(userService.registerUser(
                org.mockito.ArgumentMatchers.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, String> result = authController.registerUser(request);

        assertEquals("User registered successfully", result.get("message"));

        verify(userService).registerUser(
                org.mockito.ArgumentMatchers.any(User.class));
    }
    
    @Test
    void registerUser_whenUsernameAlreadyExists_throwsBadRequest()
    {
        RegisterRequest request = new RegisterRequest();

        request.setUsername("john");
        request.setEmail("john@gmail.com");
        request.setPassword("12345");

        when(userService.registerUser(
                org.mockito.ArgumentMatchers.any(User.class)))
                .thenThrow(new IllegalArgumentException("Username is already taken"));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authController.registerUser(request)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Username is already taken", exception.getReason());
    }
    
    @Test
    void loginUser_whenCredentialsAreCorrect_returnsToken()
    {
        LoginRequest request = new LoginRequest();

        request.setUsername("john");
        request.setPassword("12345");

        User user = new User();
        user.setUsername("john");

        when(userService.loginUser("john", "12345"))
                .thenReturn(user);

        when(jwtService.generateToken("john"))
                .thenReturn("test-jwt-token");

        AuthResponse result = authController.loginUser(request);

        assertNotNull(result);

        verify(userService).loginUser("john", "12345");
        verify(jwtService).generateToken("john");
    }

    @Test
    void loginUser_whenCredentialsAreInvalid_throwsUnauthorized() 
    {
        LoginRequest request = new LoginRequest();

        request.setUsername("john");
        request.setPassword("wrongPassword");

        when(userService.loginUser("john", "wrongPassword"))
                .thenThrow(new IllegalArgumentException("Invalid username or password"));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authController.loginUser(request)
        );

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Invalid username or password", exception.getReason());
    }
    
    
}