package com.openclassrooms.PayMyBuddy.repositories;

import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String userName);
}
