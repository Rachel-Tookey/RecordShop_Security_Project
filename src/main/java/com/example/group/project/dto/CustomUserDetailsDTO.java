package com.example.group.project.dto;

import com.example.group.project.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CustomUserDetailsDTO implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetailsDTO(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = Stream.of(user.getRoleLink().getRole()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return this.authorities; }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() { return this.username; }

    @Override
    public boolean isAccountNonExpired() { return false; }

    @Override
    public boolean isAccountNonLocked() { return false; }

    @Override
    public boolean isCredentialsNonExpired() { return false; }

    @Override
    public boolean isEnabled() { return false; }


}
