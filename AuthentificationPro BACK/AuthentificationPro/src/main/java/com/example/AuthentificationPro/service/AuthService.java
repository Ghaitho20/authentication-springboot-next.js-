package com.example.AuthentificationPro.service;


import com.example.AuthentificationPro.dao.AuthDao;
import com.example.AuthentificationPro.model.User;
import com.example.AuthentificationPro.model.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.AuthentificationPro.SecurityConfig;
import com.example.AuthentificationPro.service.JWTService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class    AuthService {
    @Autowired
    private AuthDao authDao ;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public Map<String,String> Login(User user) {
        Optional<User> optionalUser = authDao.findByEmail(user.getEmail());

        if (optionalUser.isPresent() && (user.getPassword()).equals(optionalUser.get().getPassword())) {//        if (optionalUser.isPresent() && passwordEncoder.matches(user.getPassword(), optionalUser.get().getPassword()))
            String accessToken = jwtService.generateToken(user.getEmail());                                                                                                           //return jwtService.generateToken(user.getEmail());
            String refreshToken = jwtService.generateRefreshToken(user.getEmail());

            Map <String,String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken) ;
            tokens.put("refresh_token", refreshToken);


            return tokens;
        } else {
            throw new IllegalArgumentException("Invalid credentials");


            //return "Invalid credentials";  // Return a response, not a 403
        }
    }

    public String Register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authDao.save(user);
        return "User registered successfully";
    }



    //public String Login(User user){


    //User u1 = new User();
    //u1.setEmail(userdao.getEmail());

    //Example <User> example = Example.of(u1);

    //Optional<User> optional = authDao.findByEmail(user.getEmail());

    //if(optional.isPresent()){
    //  if(optional.get().getPassword().equals(user.getPassword())){
    //    return "success";
    //}else{
    //  return "fail";
    //}
    //}else{
    // return "faile";
    //}
    //}


    public String refreshAccessToken(String refreshToken ) {
        String email = jwtService.extractEmail(refreshToken);  // Extract username from refresh token
        if (!(jwtService.isTokenExpired(refreshToken))){
            return jwtService.generateToken(email); }
        // Generate a new access token
        return "failed to refresh access token";
    }





}
