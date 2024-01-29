package com.openclassrooms.PayMyBuddy;

import com.openclassrooms.PayMyBuddy.model.Fee;
import com.openclassrooms.PayMyBuddy.model.User;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class TransfertControllerTest {

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
    @Autowired
    private MockMvc mvc;

    @Before
    public void initUsers() {

        transactionRepository.deleteAll();
        cleanUsers();

        User user = new User();

        user.setUsername("titi@titi.fr");
        user.setFirstname("titi");
        user.setLastname("toto");
        user.seteMail("titi@titi.fr");
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode("tutu"));
        user.setBalance(2500);

        userService.save(user);

        User friend = new User();

        friend.setUsername("toto@toto.fr");
        friend.setFirstname("toto");
        friend.setLastname("tutu");
        friend.seteMail("toto@toto.fr");
        friend.setRole("ROLE_USER");
        friend.setPassword(passwordEncoder.encode("tutu"));
        friend.setBalance(3800);

        userService.save(friend);


    }

    @After
    public void cleanUsers(){
        transactionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testTransferPage() throws Exception {
        mvc.perform(get("/transfer")
                        .with(csrf()).with(user("titi@titi.fr").password("tutu").roles("USER","ADMIN"))
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("transfer"));
    }

    @Test
    public void testTransaction() throws Exception {

        float amount = 20;
        String userMail = "titi@titi.fr";
        String friendMail = "toto@toto.fr";
        User user = null;
        User friend = null;

        //on récuperre l'user
        Optional<User> optUser = userService.findByEmail(userMail);
        if(optUser.isPresent()){
            user = optUser.get();
        }
        //on récupèrre le friend
        Optional<User> optFriend = userService.findByEmail(friendMail);
        if(optFriend.isPresent()){
            friend = optFriend.get();
        }

        float userBalanceBeforeTransaction = user.getBalance();
        float friendBalanceBeforeTransaction = friend.getBalance();

        mvc.perform(post("/transfer")
                .with(user("titi@titi.fr").password("tutu").roles("USER","ADMIN"))
                .param("email", "toto@toto.fr")
                .param("amount", "20")
                .param("description", "restaurant")
                .with(csrf())
        ).andExpect(redirectedUrl("/transfer?success"));

        //on récuperre l'user apres la transaction
        Optional<User> optUser2 = userService.findByEmail(userMail);
        if(optUser2.isPresent()){
            user = optUser2.get();
        }
        //on récupèrre le friend apres la transaction
        Optional<User> optFriend2 = userService.findByEmail(friendMail);
        if(optFriend2.isPresent()){
            friend = optFriend2.get();
        }

        float userBalanceAfterTransaction = user.getBalance();
        float friendBalanceAfterTransaction = friend.getBalance();

        float userCurrentBalance = userBalanceBeforeTransaction - (amount + ((amount * Fee.FEE_RATE/100)));
        float friendCurrentBalance = friendBalanceBeforeTransaction + amount;

        //check si le montant à bien été retranché à l'user
        Assert.assertEquals(userBalanceAfterTransaction,userCurrentBalance, 1);
        //check si le montant à bien été ajouté au friend
        Assert.assertEquals(friendBalanceAfterTransaction,friendCurrentBalance, 1);

    }



}
