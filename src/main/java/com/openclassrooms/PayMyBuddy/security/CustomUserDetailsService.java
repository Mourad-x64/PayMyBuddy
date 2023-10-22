package com.openclassrooms.PayMyBuddy.security;

import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> opt = userRepository.findByUsername(userName);
        if(!opt.isPresent()){
            throw new UsernameNotFoundException("Username not found.");
        }
        User user = opt.get();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.isAdmin()));
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private List< ? extends GrantedAuthority> mapRolesToAuthorities(boolean isAdmin) {
        List<SimpleGrantedAuthority> mapRoles = new ArrayList<>();
        if(isAdmin){
            mapRoles.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return mapRoles;
    }
}