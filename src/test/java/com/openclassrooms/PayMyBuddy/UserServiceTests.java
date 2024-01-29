package com.openclassrooms.PayMyBuddy;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.repositories.UserRepository;
import com.openclassrooms.PayMyBuddy.services.UserService;
import org.junit.After;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "test")
public class UserServiceTests {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;




    @Before
    public void initUser() {

        cleanUser();

        User user = new User();

        float balance = 2000;

        user.setUsername("titi@titi.fr");
        user.setFirstname("titi");
        user.setLastname("toto");
        user.seteMail("titi@titi.fr");
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode("tutu"));
        user.setBalance(balance);

        userService.save(user);
    }

    @After
    public void cleanUser(){
        userRepository.deleteAll();
    }

    @Test
    public void testFindByMail(){
        Optional<User> optUser = userService.findByEmail("titi@titi.fr");

        Assert.assertTrue(optUser.isPresent());
    }







}
