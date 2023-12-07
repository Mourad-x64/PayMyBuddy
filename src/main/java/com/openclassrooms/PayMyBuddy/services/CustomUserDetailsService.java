package com.openclassrooms.PayMyBuddy.services;

import com.openclassrooms.PayMyBuddy.model.Role;
import com.openclassrooms.PayMyBuddy.model.User;
import com.openclassrooms.PayMyBuddy.model.dto.UserDto;
import com.openclassrooms.PayMyBuddy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {

                Optional<User> opt = userRepository.findByeMail(eMail);
                if(opt.isPresent()){
                    User user = opt.get();
                    return new org.springframework.security.core.userdetails.User(user.geteMail(), user.getPassword(), mapAuthorities(user.getRole()));
                }else{
                    throw new UsernameNotFoundException("User not found");
                }
    }


    public List<GrantedAuthority> mapAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.name()));

        return authorities;

    }

    public User save(UserDto userDto) {
        User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setUsername(userDto.getEmail());
        user.seteMail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }



}
