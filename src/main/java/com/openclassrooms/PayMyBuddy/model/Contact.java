package com.openclassrooms.PayMyBuddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @Column(name = "contact_id")
    @GeneratedValue
    private Long id;

    private String eMail;

    private String firstname;

    private String lastname;


}
