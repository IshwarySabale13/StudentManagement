package com.student.studentmanagement.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JwtServiceTest 
{
    private final String secret =
            "MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDE=";

    private final JwtService jwtService =
            new JwtService(secret, 3600000);
    
    @Test
    void generateToken_whenUsernameIsValid_returnsToken()
    {
        String token = jwtService.generateToken("john");

        assertNotNull(token);

        assertFalse(token.isEmpty());
    }
    
    @Test
    void extractUsername_whenTokenIsValid_returnsUsername()
    {
        String token = jwtService.generateToken("john");

        String username = jwtService.extractUsername(token);

        assertEquals("john", username);
    }
    
    @Test
    void isTokenValid_whenUsernameMatches_returnsTrue()
    {
        String token = jwtService.generateToken("john");

        boolean result = jwtService.isTokenValid(token, "john");

        assertTrue(result);
    }
    
    @Test
    void isTokenValid_whenUsernameDoesNotMatch_returnsFalse() 
    {
        String token = jwtService.generateToken("john");

        boolean result = jwtService.isTokenValid(token, "alex");

        assertFalse(result);
    }
    
    @Test
    void isTokenValid_whenTokenIsInvalid_returnsFalse()
    {
        String invalidToken = "invalid.token.value";

        boolean result = jwtService.isTokenValid(invalidToken, "john");

        assertFalse(result);
    }
    
    @Test
    void isTokenValid_whenTokenIsExpired_returnsFalse() 
    {
        JwtService expiredJwtService = new JwtService(secret, -1000);

        String token = expiredJwtService.generateToken("john");

        boolean result = expiredJwtService.isTokenValid(token, "john");

        assertFalse(result);
    }

}