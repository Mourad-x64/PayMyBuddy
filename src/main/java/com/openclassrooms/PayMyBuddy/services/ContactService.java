package com.openclassrooms.PayMyBuddy.services;

import com.openclassrooms.PayMyBuddy.model.Contact;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.model.dto.ContactDto;
import com.openclassrooms.PayMyBuddy.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> findByEmail(String eMail){ return contactRepository.findByeMail(eMail); }

    public Contact save(User contactAccount){
        Contact contact = new Contact();
        contact.seteMail(contactAccount.geteMail());
        contact.setFirstname(contactAccount.getFirstname());
        contact.setLastname(contactAccount.getLastname());

        return contactRepository.save(contact);
    }
}
