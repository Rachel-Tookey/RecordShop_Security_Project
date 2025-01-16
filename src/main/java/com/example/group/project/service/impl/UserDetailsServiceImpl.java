package com.example.group.project.service.impl;

import com.example.group.project.model.entity.User;
import com.example.group.project.model.entity.Role;
import com.example.group.project.model.repository.UserRepository;
import com.example.group.project.model.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

import static java.lang.Long.parseLong;


@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository UserRepository;

    private final RoleRepository RoleRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {

        this.UserRepository = userRepository;
        this.RoleRepository = roleRepository;
    }

    @Autowired
    private PasswordEncoder PasswordEncoder;

    public User findByUsername(String username){
        return UserRepository.findByUsername(username);
    }

    public boolean checkUserExists(String username){
        return UserRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading username");
        User user = findByUsername(username);
        log.info(user.getRoleLink().getRole());

        {
            if(user != null) {
                log.info("Username found");

                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoleLink().getRole())
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        }
    }

    public String hashPassword(String rawPassword){
        log.info("Hashing password");
        return PasswordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String storedHashedPassword){
        log.info("Verifying password");
        return PasswordEncoder.matches(rawPassword, storedHashedPassword);
    }

    public void saveUser(HashMap<String, String> newUser){
        // get the Role reference
        Role newUserRoleLink = RoleRepository.getReferenceById(parseLong(newUser.get("role")));
        log.info("New user role is ", newUserRoleLink.getRole());

        User returnUser = User.builder()
                .firstname(newUser.get("firstname"))
                .lastname(newUser.get("lastname"))
                .username(newUser.get("username"))
                .password(hashPassword(newUser.get("password")))
                .roleLink(newUserRoleLink)
                .build();

        UserRepository.save(returnUser);
        log.info("New user saved");
    }


}
