package com.example.AuthentificationPro.contoller;


import com.example.AuthentificationPro.model.User;
import com.example.AuthentificationPro.model.UserDao;
import com.example.AuthentificationPro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthContoller {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody User user) {
        String token = authService.Login(user);
        if (token.equals("Invalid credentials")) {
            return ResponseEntity.status(401).body(token);
        }
        return ResponseEntity.ok(token);//ResponseEntity.status(200).body(token)
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody User user) {
        String message = authService.Register(user);
        return ResponseEntity.ok(message);
    }






}
