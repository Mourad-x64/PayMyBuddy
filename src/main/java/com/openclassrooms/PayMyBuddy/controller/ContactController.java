package com.openclassrooms.PayMyBuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @GetMapping("/contact")
    String contact() {
        return "contact";
    }

    @PostMapping("/contact/add")
    String addContact() {
        return "contact";
    }

    @GetMapping("/contact/delete")
    String deleteContact() {
        return "contact";
    }

}
