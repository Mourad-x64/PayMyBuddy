package com.openclassrooms.PayMyBuddy;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.repositories.UserRepository;
import com.openclassrooms.PayMyBuddy.services.CustomUserDetailsService;
import com.openclassrooms.PayMyBuddy.services.UserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = {"test", "secret"})
public class ControllerTests {

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

        double balance = 2000;

        user.setUsername("titi@titi.fr");
        user.setFirstname("titi");
        user.setLastname("toto");
        user.seteMail("titi@titi.fr");
        user.setRole("ROLE_ADMIN");
        user.setPassword(passwordEncoder.encode("tutu"));
        user.setBalance(balance);

        userService.save(user);
    }

    @After
    public void cleanUser(){
        userRepository.deleteAll();
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
    @Ignore
    public void testRegister() throws Exception {

        mvc.perform(post("/register")
                .param("firstname", "titi")
                .param("lastName", "toto")
                .param("email", "titi")
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
    public void shouldReturnAdminView() throws Exception {

        mvc.perform(get("/admin")
                        .with(SecurityMockMvcRequestPostProcessors.user(customUserDetailsService.loadUserByUsername("titi@titi.fr"))))
                .andExpect(status().is(200));
    }



}
