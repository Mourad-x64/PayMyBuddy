package com.openclassrooms.PayMyBuddy.repositories;

import com.openclassrooms.PayMyBuddy.model.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {



}
