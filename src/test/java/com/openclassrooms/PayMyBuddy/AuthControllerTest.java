package com.openclassrooms.PayMyBuddy;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.repositories.UserRepository;
import com.openclassrooms.PayMyBuddy.services.CustomUserDetailsService;
import com.openclassrooms.PayMyBuddy.services.UserService;
import org.junit.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class AuthControllerTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mvc;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

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

        User admin = new User();

        admin.setUsername("toto@toto.fr");
        admin.setFirstname("toto");
        admin.setLastname("tutu");
        admin.seteMail("toto@toto.fr");
        admin.setRole("ROLE_ADMIN");
        admin.setPassword(passwordEncoder.encode("tutu"));
        admin.setBalance(balance);

        userService.save(admin);

    }

    @After
    public void cleanUser(){
        userRepository.deleteAll();
    }

    @Test
    public void testShowRegisterForm() throws Exception {

        mvc.perform(get("/register")
                .with(csrf())
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));

    }

    @Test
    public void testShowLoginForm() throws Exception {

        mvc.perform(get("/login")
                        .with(csrf())
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));

    }


    @Test
    public void testLogin() throws Exception {

        mvc.perform(post("/login")
                .param("username", "titi@titi.fr")
                .param("password", "tutu")
                .with(csrf())
        ).andExpect(redirectedUrl("/paymybuddy"));

        mvc.perform(post("/login")
                .param("username", "titi@titi2.fr")
                .param("password", "tutu2")
                .with(csrf())
        ).andExpect(redirectedUrl("/login?error"));

    }

    @Test
    public void testRegister() throws Exception {

        mvc.perform(post("/register")
                .param("firstname", "tata")
                .param("lastname", "toto")
                .param("email", "tata@tata.fr")
                .param("password", "tutu")
                .with(csrf())
        ).andExpect(redirectedUrl("/register?success"));

    }





}
