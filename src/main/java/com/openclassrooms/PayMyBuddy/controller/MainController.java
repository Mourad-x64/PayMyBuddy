package com.openclassrooms.PayMyBuddy.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/paymybuddy")
    String paymybuddy() {
        return "paymybuddy";
    }

}
