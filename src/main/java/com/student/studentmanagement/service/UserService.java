package com.student.studentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.studentmanagement.model.User;
import com.student.studentmanagement.repository.UserRepository;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user)
    {
        if (userRepository.existsByUsername(user.getUsername()))
        {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.existsByEmail(user.getEmail()))
        {
            throw new IllegalArgumentException("Email is already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    
    public User loginUser(String username, String rawPassword) 
    {
    	User user = userRepository.findByUsername(username)
            .orElseThrow(
            		() -> new IllegalArgumentException("Invalid username or password")
            		);

    	if (!passwordEncoder.matches(rawPassword, user.getPassword())) 
    	{
    		throw new IllegalArgumentException("Invalid username or password");
    	}

    	return user;
    }

}
