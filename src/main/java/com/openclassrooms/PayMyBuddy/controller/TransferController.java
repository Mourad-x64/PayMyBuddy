package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.Fee;
import com.openclassrooms.PayMyBuddy.model.Friendship;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.model.dto.FriendshipDto;
import com.openclassrooms.PayMyBuddy.model.dto.TransactionDto;
import com.openclassrooms.PayMyBuddy.services.FriendshipService;
import com.openclassrooms.PayMyBuddy.services.TransactionService;
import com.openclassrooms.PayMyBuddy.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TransferController {

    UserService userService;
    FriendshipService friendshipService;
    TransactionService transactionService;

    @Autowired
    public TransferController(UserService userService, FriendshipService friendshipService, TransactionService transactionService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.transactionService = transactionService;
    }

    @GetMapping("/transfer")
    String transfer(Model model, Authentication auth) {

        String email = auth.getName();
        User user = null;
        List<User> friends = new ArrayList<>();

        Optional<User> optUser = userService.findByEmail(email);
        if(optUser.isPresent()){
            user = optUser.get();
        }

        for(Friendship friendship :  user.getFriendships()){
            friends.add(friendship.getTgt());
        }

        // create model object to store form data
        TransactionDto transaction = new TransactionDto();
        model.addAttribute("postedTransaction", transaction);
        model.addAttribute("friends", friends);
        model.addAttribute("transactions", user.getTransactions());


        return "transfer";
    }


    @PostMapping("/transfer")
    String addTransaction(@ModelAttribute("postedTransaction") @Valid TransactionDto postedTransaction, BindingResult result, Model model, Authentication auth) {
        User user = null;
        User friend = null;
        double totalAmount = postedTransaction.getAmount()+(postedTransaction.getAmount() * Fee.FEE_RATE)/100;
        List<User> friends = new ArrayList<>();


        Optional<User> optUser = userService.findByEmail(auth.getName());
        if(optUser.isPresent()){
            user = optUser.get();
        }

        Optional<User> optFriend = userService.findByEmail(postedTransaction.getEmail());
        if(optFriend.isPresent()){
            friend = optFriend.get();
        }

        for(Friendship friendship :  user.getFriendships()){
            friends.add(friendship.getTgt());
        }

        model.addAttribute("postedTransaction", postedTransaction);
        model.addAttribute("friends", friends);
        model.addAttribute("transactions", user.getTransactions());


        if(user.getBalance() < totalAmount){
            return "redirect:/transfer?error";
        }else{
            transactionService.doTransaction(postedTransaction, auth.getName());
            return "redirect:/transfer?success";
        }

    }



    }
