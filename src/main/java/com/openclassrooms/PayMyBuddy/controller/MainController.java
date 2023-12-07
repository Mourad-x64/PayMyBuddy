package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class MainController {

    UserService userService;

     @Autowired
     public MainController(UserService userService){
         this.userService = userService;
     }

    @GetMapping("/paymybuddy")
    String paymybuddy(Model model,Authentication auth) {

        String email = auth.getName();
        User user = null;

        Optional<User> optUser = userService.findByEmail(email);
        if(optUser.isPresent()){
            user = optUser.get();
        }

        model.addAttribute("balance", Math.round(user.getBalance() * 100.0) / 100.0);

        return "paymybuddy";
    }

}
