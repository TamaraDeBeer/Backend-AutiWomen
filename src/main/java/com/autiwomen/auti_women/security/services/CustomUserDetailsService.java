package com.autiwomen.auti_women.security.services;

import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isEmpty()) {
            throw new RecordNotFoundException("User not found: " + username);
        } else {
            User foundUser = optionalUser.get();
            String password = foundUser.getPassword();
            Set<Authority> authorities = foundUser.getAuthorities();

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Authority authority : authorities) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }

            return new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);
        }
    }

}
