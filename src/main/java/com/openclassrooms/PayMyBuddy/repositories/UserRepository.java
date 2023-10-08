package com.openclassrooms.PayMyBuddy.repositories;

import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
