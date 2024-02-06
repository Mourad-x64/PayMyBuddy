package com.openclassrooms.PayMyBuddy;


import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.repositories.TransactionRepository;
import com.openclassrooms.PayMyBuddy.repositories.UserRepository;
import com.openclassrooms.PayMyBuddy.services.TransactionService;
import com.openclassrooms.PayMyBuddy.services.UserService;
import org.junit.After;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class MainControllerTest {

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

        User admin = new User();

        admin.setUsername("toto@toto.fr");
        admin.setFirstname("toto");
        admin.setLastname("tutu");
        admin.seteMail("toto@toto.fr");
        admin.setRole("ROLE_ADMIN");
        admin.setPassword(passwordEncoder.encode("tutu"));
        admin.setBalance(3800);

        userService.save(admin);


    }

    @After
    public void cleanUsers(){
        transactionRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    public void testMainPage() throws Exception {
        mvc.perform(get("/paymybuddy")
                        .with(csrf()).with(user("titi@titi.fr").password("tutu").roles("USER","ADMIN"))
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("paymybuddy"));
    }

}
