package com.example.AuthentificationPro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.AuthentificationPro.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthDao extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
}
