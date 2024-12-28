package com.example.AuthentificationPro;

import com.example.AuthentificationPro.service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken("testUser");
        System.out.println("Generated Token: " + token);
        assert token != null && !token.isEmpty();
    }
}
