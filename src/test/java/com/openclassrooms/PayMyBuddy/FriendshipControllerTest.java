package com.openclassrooms.PayMyBuddy;

import com.openclassrooms.PayMyBuddy.model.Friendship;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.repositories.FriendshipRepository;
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
public class FriendshipControllerTest {

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
    FriendshipRepository friendshipRepository;
    @Autowired
    private MockMvc mvc;

    @Before
    public void initUsers() {
        friendshipRepository.deleteAll();
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
        friendshipRepository.deleteAll();
        transactionRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    public void testFriendsPage() throws Exception {
        mvc.perform(get("/friends")
                        .with(csrf()).with(user("titi@titi.fr").password("tutu").roles("USER","ADMIN"))
                ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("friends"));
    }

    @Test
    public void testAddFriend() throws Exception {

        mvc.perform(post("/friends")
                        .param("email", "toto@toto.fr")
                        .with(csrf()).with(user("titi@titi.fr").password("tutu").roles("USER","ADMIN")))
                        .andExpect(redirectedUrl("/friends?success"));


        //on récuperre l'user
        User user = null;
        Optional<User> optUser = userService.findByEmail("titi@titi.fr");
        if(optUser.isPresent()){
            user = optUser.get();
        }

        Friendship friend = null;
        Optional<Friendship> optFriend = friendshipRepository.getBySrc(user);
        if(optUser.isPresent()){
            friend = optFriend.get();
        }

        Assert.assertEquals("toto@toto.fr", friend.getTgt().geteMail());


    }




}
