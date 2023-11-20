package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.Contact;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.model.dto.ContactDto;
import com.openclassrooms.PayMyBuddy.model.dto.UserDto;
import com.openclassrooms.PayMyBuddy.services.ContactService;
import com.openclassrooms.PayMyBuddy.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

import jakarta.validation.Valid;

@Controller
public class ContactController {

    UserService userService;
    ContactService contactService;

    @Autowired
    public ContactController(UserService userService, ContactService contactService) {

        this.userService = userService;
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    String contact(Model model) {
        //fetch all contacts
        model.addAttribute("contacts", userService.getAllContacts());
        // create model object to store form data
        ContactDto contact = new ContactDto();
        model.addAttribute("postedContact", contact);
        return "contact";
    }

    @PostMapping("/contact/add")
    String addContact(@ModelAttribute("postedContact") @Valid ContactDto postedContact, BindingResult result, Model model) {

        Optional<User> optUser = userService.findByEmail(postedContact.getEmail());
        if(optUser.isPresent()){
            User contactAccount = optUser.get();
            contactService.save(contactAccount);
        }else {
            result.rejectValue("email", null,
                    "no known account with this eMail !");
        }

        if(result.hasErrors()){
            model.addAttribute("user", postedContact);
            return "contact";
        }else{
            return "redirect:/contact?success";
        }

    }

    @GetMapping("/contact/delete")
    String deleteContact() {
        return "contact";
    }

}
