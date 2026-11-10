package com.student.studentmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.student.studentmanagement.model.User;
import com.student.studentmanagement.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest 
{
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_whenUsernameAlreadyExists_throwsException() 
    {
        User user = new User();
        user.setUsername("john");

        when(userRepository.existsByUsername("john"))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(user)
        );

        assertEquals("Username is already taken", exception.getMessage());

        verify(userRepository).existsByUsername("john");
    }
    
    @Test
    void registerUser_whenEmailAlreadyExists_throwsException()
    {
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@gmail.com");

        when(userRepository.existsByUsername("john"))
                .thenReturn(false);

        when(userRepository.existsByEmail("john@gmail.com"))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(user)
        );

        assertEquals("Email is already registered", exception.getMessage());

        verify(userRepository).existsByEmail("john@gmail.com");
    }
    
    @Test
    void registerUser_whenDetailsAreValid_savesUser()
    {
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@gmail.com");
        user.setPassword("12345");

        when(userRepository.existsByUsername("john"))
                .thenReturn(false);

        when(userRepository.existsByEmail("john@gmail.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("12345"))
                .thenReturn("encodedPassword");

        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals("encodedPassword", result.getPassword());

        verify(userRepository).save(user);
    }
    
    @Test
    void loginUser_whenCredentialsAreCorrect_returnsUser()
    {
        User user = new User();
        user.setUsername("john");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("12345", "encodedPassword"))
                .thenReturn(true);

        User result = userService.loginUser("john", "12345");

        assertEquals("john", result.getUsername());

        verify(userRepository).findByUsername("john");
    }
    
    @Test
    void loginUser_whenUsernameDoesNotExist_throwsException()
    {
        when(userRepository.findByUsername("wronguser"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.loginUser("wronguser", "12345")
        );

        assertEquals("Invalid username or password", exception.getMessage());
    }
    
    @Test
    void loginUser_whenPasswordIsIncorrect_throwsException() 
    {
        User user = new User();
        user.setUsername("john");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrongPassword", "encodedPassword"))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.loginUser("john", "wrongPassword")
        );

        assertEquals("Invalid username or password", exception.getMessage());
    }
}