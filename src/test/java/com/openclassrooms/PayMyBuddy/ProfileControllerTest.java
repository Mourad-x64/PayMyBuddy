package com.openclassrooms.PayMyBuddy;

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
public class ProfileControllerTest {

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
    public void testProfilePage() throws Exception {
        mvc.perform(get("/profile")
                        .with(csrf()).with(user("titi@titi.fr").password("tutu").roles("USER","ADMIN"))
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("profile"));
    }

    @Test
    public void testSaveProfile() throws Exception {
        mvc.perform(post("/profile")
                .with(user("titi@titi.fr").password("tutu").roles("USER","ADMIN"))
                .param("firstname", "tata")
                .param("lastname", "tutu")
                .param("email", "tata@tata.fr")
                .param("password", "toto")
                .with(csrf())
        ).andExpect(redirectedUrl("/login?saved"));

        mvc.perform(post("/login")
                .param("username", "tata@tata.fr")
                .param("password", "toto")
                .with(csrf())
        ).andExpect(redirectedUrl("/paymybuddy"));

        User user = null;

        //on récuperre l'user
        Optional<User> optUser = userService.findByEmail("tata@tata.fr");
        if(optUser.isPresent()){
            user = optUser.get();
        }

        Assert.assertEquals("tata", user.getFirstname());
        Assert.assertEquals("tutu", user.getLastname());

    }

    @Test
    public void testTransferWithBank() throws Exception {

        User user = null;

        //on récuperre l'user
        Optional<User> optUser = userService.findByEmail("titi@titi.fr");
        if(optUser.isPresent()){
            user = optUser.get();
        }

        float balance = user.getBalance();

        //transfer 25 to bank
        mvc.perform(post("/profile/toBank")
                .with(user("titi@titi.fr").password("tutu").roles("USER","ADMIN"))
                .param("transferToOrFromBank", "25")
                .with(csrf())
        ).andExpect(redirectedUrl("/profile?transferToBankSuccess"));

        Optional<User> optUser2 = userService.findByEmail("titi@titi.fr");
        if(optUser2.isPresent()){
            user = optUser2.get();
        }

        Assert.assertEquals((balance-25), user.getBalance(), 0);

        balance = user.getBalance();

        //transfer 25 from bank
        mvc.perform(post("/profile/fromBank")
                .with(user("titi@titi.fr").password("tutu").roles("USER","ADMIN"))
                .param("transferToOrFromBank", "25")
                .with(csrf())
        ).andExpect(redirectedUrl("/profile?transferToBankSuccess"));

        Optional<User> optUser3 = userService.findByEmail("titi@titi.fr");
        if(optUser3.isPresent()){
            user = optUser3.get();
        }

        Assert.assertEquals((balance+25), user.getBalance(), 0);


    }

}
