package com.example.group.project.service.impl;

import com.example.group.project.model.entity.User;
import com.example.group.project.model.repository.UserRepository;
import com.example.group.project.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Override
    public List<User> getAllUsers(){
        return UserRepository.findAll();
    }

    @Override
    public User findByUsername(String username){
        return UserRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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


}
