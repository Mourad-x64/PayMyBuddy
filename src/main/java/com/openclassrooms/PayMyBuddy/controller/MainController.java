package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.Role;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.repositories.TransactionRepository;
import com.openclassrooms.PayMyBuddy.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Optional;

@Controller
public class MainController {

    UserService userService;
    TransactionRepository transactionRepository;

     @Autowired
     public MainController(UserService userService, TransactionRepository transactionRepository){
         this.userService = userService;
         this.transactionRepository = transactionRepository;
     }

    @GetMapping("/paymybuddy")
    String paymybuddy(Model model,Authentication auth, HttpServletResponse response) {


        String email = auth.getName();
        User user = null;

        Optional<User> optUser = userService.findByEmail(email);
        if(optUser.isPresent()){
            user = optUser.get();
        }

        model.addAttribute("balance", Math.round(user.getBalance() * 100.0) / 100.0);

        return "paymybuddy";


    }

    @GetMapping("/admin")
    String admin(Authentication auth, Model model){

         double revenue = Optional.ofNullable(transactionRepository.findRevenue()).orElse(0.00);

        model.addAttribute("revenue", Math.round(revenue * 100.0) / 100.0);

         return "admin";
    }

}
