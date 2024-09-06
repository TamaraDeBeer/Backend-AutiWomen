package com.autiwomen.auti_women.security.services;

import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.dtos.user.UserOutputDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.utils.RandomStringGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(toUserOutputDto(user));
        }
        return collection;
    }

    public UserOutputDto getOneUser(String username) {
        UserOutputDto dto = new UserOutputDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            dto = toUserOutputDto(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    //    Deze methode is enkel voor de CustomUserDetailsService omdat we daar een wachtwoord nodig hebben en de UserOutputDto geen wachtwoord bevat
    public UserDto getUserEntity(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return fromUser(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

//    public boolean userExists(String username) {
//        return userRepository.existsById(username);
//    }

    public String createUser(UserInputDto userInputDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userInputDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userInputDto));
        newUser.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        return newUser.getUsername();
    }


    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public UserDto updatePasswordUser(String username, UserDto updateUser) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            userRepository.save(user);
            return fromUser(user);
        } else {
            throw new RecordNotFoundException("Er is geen user gevonden met username: " + username);
        }
    }

    public Set<Authority> getAuthoritiyByUsername(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = fromUser(user);
            return userDto.getAuthorities();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public UserDto addAuthority(String username, String authority) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.addAuthority(new Authority(username, authority));
            userRepository.save(user);
            return fromUser(user);
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public void removeAuthority(String username, String authority) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Authority> authorityToRemove = user.getAuthorities().stream()
                    .filter(a -> a.getAuthority().equalsIgnoreCase(authority))
                    .findAny();
            if (authorityToRemove.isPresent()) {
                user.removeAuthority(authorityToRemove.get());
                userRepository.save(user);
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public static UserDto fromUser(User user) {

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();

        return dto;
    }

    public User toUser(UserInputDto userInputDto) {
        var user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
        user.setEnabled(userInputDto.getEnabled());
        user.setApikey(userInputDto.getApikey());
        user.setEmail(userInputDto.getEmail());
        return user;
    }

    public static UserOutputDto toUserOutputDto(User user) {
        return new UserOutputDto(user.getUsername(), user.getEmail());
    }

}
