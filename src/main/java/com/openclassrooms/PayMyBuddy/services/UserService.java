package com.openclassrooms.PayMyBuddy.services;

import com.openclassrooms.PayMyBuddy.model.Friendship;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String eMail){ return userRepository.findByeMail(eMail); }

    public User save(User user){
        return userRepository.save(user);
    }

}


