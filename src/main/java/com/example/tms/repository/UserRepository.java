package com.example.tms.repository;

import com.example.tms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    public Optional<User> findByEmail(String email);
    boolean existsByName(String username);
    boolean existsByEmail(String email);
}
