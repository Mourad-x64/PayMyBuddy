package com.openclassrooms.PayMyBuddy;

import com.openclassrooms.PayMyBuddy.model.Transaction;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.model.dto.TransactionDto;
import com.openclassrooms.PayMyBuddy.repositories.TransactionRepository;
import com.openclassrooms.PayMyBuddy.repositories.UserRepository;
import com.openclassrooms.PayMyBuddy.services.TransactionService;
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
public class TransactionServiceTests {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TransactionService transactionService;
    @Autowired
    TransactionRepository transactionRepository;


    @Before
    public void initUsers() {

        cleanUsers();

        User user = new User();

        user.setUsername("titi@titi.fr");
        user.setFirstname("titi");
        user.setLastname("toto");
        user.seteMail("titi@titi.fr");
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode("tutu"));
        user.setBalance(2500.00);

        userService.save(user);

        User friend = new User();

        friend.setUsername("toto@toto.fr");
        friend.setFirstname("toto");
        friend.setLastname("tutu");
        friend.seteMail("toto@toto.fr");
        friend.setRole("ROLE_USER");
        friend.setPassword(passwordEncoder.encode("tutu"));
        friend.setBalance(3800.00);

        userService.save(friend);


    }

    @After
    public void cleanUsers(){
        transactionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testDoTransaction(){

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(25.00);
        transactionDto.setEmail("toto@toto.fr");
        transactionDto.setDescription("restaurant");

        String userMail = "titi@titi.fr";

        Transaction transaction = transactionService.doTransaction(transactionDto, userMail);

        Optional<Transaction> optDbTransaction = transactionRepository.getByAmount(25.00);
        //test si la transaction est créee
        Assert.assertTrue(optDbTransaction.isPresent());

    }

}
