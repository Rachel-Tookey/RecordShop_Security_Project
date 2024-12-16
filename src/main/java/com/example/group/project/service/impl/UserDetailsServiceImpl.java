package com.example.group.project.service.impl;

import com.example.group.project.model.entity.User;
import com.example.group.project.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository UserRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.UserRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder PasswordEncoder;

    public List<User> getAllUsers(){
        return UserRepository.findAll();
    }

    public User findByUsername(String username){
        return UserRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading username");
        User user = findByUsername(username);
        {
            if(user != null) {
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        }
    }

    public String hashPassword(String rawPassword){
        return PasswordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String storedHashedPassword){
        return PasswordEncoder.matches(rawPassword, storedHashedPassword);
    }

    public void saveUser(User newUser){
        UserRepository.save(newUser);
    }


}
