package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.model.dto.TransferDto;
import com.openclassrooms.PayMyBuddy.model.dto.UserDto;
import com.openclassrooms.PayMyBuddy.services.UserService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ProfileController {


    UserService userService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    String profile(Model model, Authentication auth) {

        String email = auth.getName();
        User user = null;

        Optional<User> optUser = userService.findByEmail(email);
        if(optUser.isPresent()){
            user = optUser.get();
        }

        model.addAttribute("balance", Math.round(user.getBalance() * 100.0) / 100.0);

        UserDto userDto = new UserDto();
        TransferDto transferDto = new TransferDto();
        model.addAttribute("postedProfile", userDto);
        model.addAttribute("postedTransferToBank", transferDto);
        model.addAttribute("postedTransferFromBank", transferDto);

        return "profile";
    }

    @PostMapping("/profile/toBank")
    String transferToBank(@ModelAttribute("postedTransferToBank") @Valid TransferDto postedTransferToBank, BindingResult result, Model model, Authentication auth) {

        User user = null;

        float amount = postedTransferToBank.getTransferToOrFromBank();

        Optional<User> optUser = userService.findByEmail(auth.getName());
        if(optUser.isPresent()){
            user = optUser.get();
        }

        float balance = user.getBalance();

        if(amount > balance){
            return "redirect:/profile?transferNotEnoughMoney";
        }

        user.setBalance(Math.round((balance - amount) * 100)/100);

        user = userService.save(user);

        if(user.getBalance() == Math.round((balance - amount) * 100)/100) {
            return "redirect:/profile?transferToBankSuccess";
        }else {
            return "redirect:/profile?transferToBankError";
        }
    }

    @PostMapping("/profile/fromBank")
    String transferFromBank(@ModelAttribute("postedTransferFromBank") @Valid TransferDto postedTransferFromBank, BindingResult result, Model model, Authentication auth) {

        User user = null;

        float amount = postedTransferFromBank.getTransferToOrFromBank();

        Optional<User> optUser = userService.findByEmail(auth.getName());
        if(optUser.isPresent()){
            user = optUser.get();
        }

        float balance = user.getBalance();


        user.setBalance(Math.round((balance + amount) * 100)/100);

        user = userService.save(user);

        if(user.getBalance() == Math.round((balance + amount) * 100)/100) {
            return "redirect:/profile?transferToBankSuccess";
        }else {
            return "redirect:/profile?transferToBankError";
        }
    }

    @PostMapping("/profile")
    String saveProfile(@ModelAttribute("postedProfile") @Valid UserDto postedProfile, BindingResult result, Model model, Authentication auth) {

        User user = null;

        Optional<User> optUser = userService.findByEmail(auth.getName());
        if(optUser.isPresent()){
            user = optUser.get();
        }

        if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(postedProfile.getFirstname()))){
            user.setFirstname(postedProfile.getFirstname());
        }

        if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(postedProfile.getLastname()))){
            user.setLastname(postedProfile.getLastname());
        }

        if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(postedProfile.getPassword()))){
            user.setPassword(passwordEncoder.encode(postedProfile.getPassword()));
        }

        if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(postedProfile.getEmail()))){
            if(!postedProfile.getEmail().equals(auth.getName())) {
                Optional<User> optPostedUser = userService.findByEmail(postedProfile.getEmail());
                if (optPostedUser.isPresent()) {
                    return "redirect:/profile?error";
                }else {
                    user.seteMail(postedProfile.getEmail());
                    user.setUsername(postedProfile.getEmail());
                }
            }
        }

        userService.save(user);

        return "redirect:/login?saved";


    }


}
