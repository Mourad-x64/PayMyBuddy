package com.openclassrooms.PayMyBuddy.repositories;

import com.openclassrooms.PayMyBuddy.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    public Optional<Contact> findByeMail(String eMail);

}
