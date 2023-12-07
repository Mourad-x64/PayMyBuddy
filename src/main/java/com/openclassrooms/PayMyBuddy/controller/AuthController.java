package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.model.dto.UserDto;
import com.openclassrooms.PayMyBuddy.services.CustomUserDetailsService;
import com.openclassrooms.PayMyBuddy.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Optional;


@Controller
class AuthController {

    UserService userService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    String index() {
        return "index";
    }

    @GetMapping("/login")
    String login(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        Cookie cookie = new Cookie("email", "toto");
        response.addCookie(cookie);

        return "login";
    }

    @GetMapping("/register")
    String showRegisterForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("postedUser", user);
        return "register";
    }

    @PostMapping("/register")
    String registerUser(@ModelAttribute("postedUser") @Valid UserDto postedUser, BindingResult result, Model model) {

        Optional<User> optUser = userService.findByEmail(postedUser.getEmail());
        if(optUser.isPresent()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }else {
            customUserDetailsService.save(postedUser);
        }

        if(result.hasErrors()){
            model.addAttribute("user", postedUser);
            return "register";
        }else{
            return "redirect:/register?success";
        }

    }

}


