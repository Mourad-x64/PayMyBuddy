package com.openclassrooms.PayMyBuddy.repositories;

import com.openclassrooms.PayMyBuddy.model.Transaction;

import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT SUM(commission) FROM transactions", nativeQuery = true)
    float findRevenue();

    Optional<Transaction> getByAmount(float v);

    Optional<Transaction> getByFrom(User user);
}
