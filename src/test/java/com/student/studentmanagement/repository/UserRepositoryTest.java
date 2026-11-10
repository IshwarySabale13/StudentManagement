package com.student.studentmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.student.studentmanagement.model.User;

@DataJpaTest
class UserRepositoryTest
{
    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser_shouldSaveUserSuccessfully() 
    {
        User user = new User();
        user.setUsername("rahul");
        user.setEmail("rahul@gmail.com");
        user.setPassword("password123");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("rahul", savedUser.getUsername());
        assertEquals("rahul@gmail.com", savedUser.getEmail());
    }
    
    @Test
    void findByUsername_shouldReturnUserSuccessfully()
    {
        User user = new User();
        user.setUsername("priya");
        user.setEmail("priya@gmail.com");
        user.setPassword("password123");

        userRepository.save(user);

        Optional<User> result =
                userRepository.findByUsername("priya");

        assertTrue(result.isPresent());
        assertEquals("priya", result.get().getUsername());
    }
    
    @Test
    void findByUsername_shouldReturnEmptyWhenUserDoesNotExist() 
    {
        Optional<User> result =
                userRepository.findByUsername("unknown");

        assertTrue(result.isEmpty());
    }
    
    @Test
    void existsByUsername_shouldReturnTrueWhenUsernameExists()
    {
        User user = new User();
        user.setUsername("amit");
        user.setEmail("amit@gmail.com");
        user.setPassword("password123");

        userRepository.save(user);

        boolean result = userRepository.existsByUsername("amit");

        assertTrue(result);
    }
    
    @Test
    void existsByUsername_shouldReturnFalseWhenUsernameDoesNotExist() 
    {
        boolean result = userRepository.existsByUsername("unknown");

        assertFalse(result);
    }
    
    @Test
    void existsByEmail_shouldReturnTrueWhenEmailExists() 
    {
        User user = new User();
        user.setUsername("neha");
        user.setEmail("neha@gmail.com");
        user.setPassword("password123");

        userRepository.save(user);

        boolean result = userRepository.existsByEmail("neha@gmail.com");

        assertTrue(result);
    }
    
    @Test
    void existsByEmail_shouldReturnFalseWhenEmailDoesNotExist()
    {
        boolean result =
                userRepository.existsByEmail("unknown@gmail.com");

        assertFalse(result);
    }
}