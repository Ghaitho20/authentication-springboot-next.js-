package com.example.AuthentificationPro.controller;


import com.example.AuthentificationPro.model.User;
import com.example.AuthentificationPro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthContoller {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody User user) {
        try{
            Map<String ,String> tokens = authService.Login(user);
            return (ResponseEntity.ok(tokens));
        }catch (Exception e){
            return (ResponseEntity.badRequest().body(e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody User user) {
        String message = authService.Register(user);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestBody String refreshToken) {
        try{
            String accessToken  = authService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(accessToken);

        }catch (Exception e){
            return (ResponseEntity.badRequest().body(e.getMessage()));
        }

    }







}
