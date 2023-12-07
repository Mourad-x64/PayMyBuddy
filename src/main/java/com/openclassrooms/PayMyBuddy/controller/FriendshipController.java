package com.openclassrooms.PayMyBuddy.controller;

import com.openclassrooms.PayMyBuddy.model.Friendship;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.model.dto.FriendshipDto;
import com.openclassrooms.PayMyBuddy.services.FriendshipService;
import com.openclassrooms.PayMyBuddy.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FriendshipController {

    UserService userService;
    FriendshipService friendshipService;

    @Autowired
    public FriendshipController(UserService userService, FriendshipService friendshipService) {

        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    @GetMapping("/friends")
    String friends(Model model, Authentication auth) {
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
        FriendshipDto friend = new FriendshipDto();
        model.addAttribute("postedFriend", friend);
        model.addAttribute("friends", friends);

        return "friends";
    }

    @PostMapping("/friends")
    String addFriend(@ModelAttribute("postedFriend") @Valid FriendshipDto postedFriend, BindingResult result, Model model, Authentication auth) {

        String userEmail = auth.getName();
        User user = null;
        User friend = null;
        Friendship newFriendship = new Friendship();
        List<User> friends = new ArrayList<>();

        //on crée l'objet user courant et on remplis sa liste de friends
        Optional<User> optUser = userService.findByEmail(userEmail);
        if(optUser.isPresent()){
            user = optUser.get();

            for(Friendship friendship :  user.getFriendships()){
                friends.add(friendship.getTgt());
            }

        }

        //on crée la nouvelle friendship en objet et on test si l'user existe
        Optional<User> optFriend = userService.findByEmail(postedFriend.getEmail());
        if(optFriend.isPresent()){

            friend = optFriend.get();
            newFriendship.setSrc(user);
            newFriendship.setTgt(friend);

            //on test si le friend n'existe pas déja
            for(Friendship userFriendship : user.getFriendships()){
                if(userFriendship.getTgt().getId() == friend.getId()){
                    result.rejectValue("email", null,
                            "This friend already exists !");
                }
            }

            //on test si le friend n'est pas l'user courant
            if(user.getId() == newFriendship.getTgt().getId()){
                result.rejectValue("email", null,
                        "You can't be friend with yourself !");
            }

        }else {
            result.rejectValue("email", null,
                    "There is no user with this email in the app !");
        }


        if(result.hasErrors()){
            model.addAttribute("friends", friends);
            return "friends";
        }else{
            //on sauvegarde la nouvelle friendship
            friendshipService.save(newFriendship);

            return "redirect:/friends?success";
        }



    }

    @GetMapping("/friends/delete/{id}")
    String deleteFriend(@PathVariable("id") Long friendId, Model model, Authentication auth) {

        String userEmail = auth.getName();
        User user = null;
        Friendship newFriendship = new Friendship();

        //on crée l'objet user courant
        Optional<User> optUser = userService.findByEmail(userEmail);
        if(optUser.isPresent()){
            user = optUser.get();
        }

        //on recherche la friendship à supprimer
        for(Friendship friendship :  user.getFriendships()){

            if(friendship.getTgt().getId().equals(friendId)){

                friendshipService.deleteFriendship(friendship.getId());
            }
        }

        return "redirect:/friends?deleted";

    }

}
