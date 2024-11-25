package com.autiwomen.auti_women.security.services;

import com.autiwomen.auti_women.exceptions.BadRequestException;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.repositories.AuthorityRepository;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorityService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public AuthorityService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll();
    }

    public Set<Authority> getUserAuthorities(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = fromUser(user);
            return userDto.getAuthorities();
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
    }

    public void addUserAuthority(String username, String authority) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean conflictingRoleExists = user.getAuthorities().stream()
                    .anyMatch(a -> (a.getAuthority().equals("ROLE_USER") && authority.equals("ROLE_ADMIN")) ||
                            (a.getAuthority().equals("ROLE_ADMIN") && authority.equals("ROLE_USER")));
            if (conflictingRoleExists) {
                throw new IllegalArgumentException("User cannot have conflicting roles");
            }
            boolean authorityExists = user.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals(authority));
            if (!authorityExists) {
                user.addAuthority(new Authority(username, authority));
                userRepository.save(user);
            } else {
                throw new BadRequestException("User already has this authority");
            }
            fromUser(user);
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
    }

    public void updateUserAuthority(String username, String oldAuthority, String newAuthority) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Authority newAuth = new Authority(username, newAuthority);

            Set<Authority> authorities = user.getAuthorities();

            Optional<Authority> oldAuthOptional = authorities.stream()
                    .filter(a -> a.getAuthority().equals(oldAuthority))
                    .findFirst();

            if (oldAuthOptional.isPresent()) {
                Authority oldAuth = oldAuthOptional.get();
                authorities.remove(oldAuth);
                authorities.add(newAuth);
                userRepository.save(user);
                authorityRepository.delete(oldAuth);
                authorityRepository.save(newAuth);
            } else {
                throw new RecordNotFoundException("Authority not found: " + oldAuthority);
            }
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
    }

    @Transactional
    public void removeUserAuthority(String username, String authority) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Authority> authorityToRemove = user.getAuthorities().stream()
                    .filter(a -> a.getAuthority().equalsIgnoreCase(authority))
                    .findAny();
            if (authorityToRemove.isPresent()) {
                user.removeAuthority(authorityToRemove.get());
                userRepository.save(user);
            } else {
                throw new RecordNotFoundException("Authority not found: " + authority);
            }
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
    }

    public UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setAuthorities(user.getAuthorities());
        return userDto;
    }

}
