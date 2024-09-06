package com.autiwomen.auti_women.security.services;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.models.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

//    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDto userDto = userService.getUserEntity(username);


        String password = passwordEncoder.encode(userDto.getPassword());

        Set<Authority> authorities = userDto.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority: authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);
    }

}
