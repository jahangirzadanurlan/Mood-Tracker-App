package com.example.userservice.repository;

import com.example.userservice.model.entity.Token;
import com.example.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findTokenByToken(String token);
    Optional<Token> findTokenByUser(User user);
}
