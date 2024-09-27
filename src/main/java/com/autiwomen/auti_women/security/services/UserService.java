package com.autiwomen.auti_women.security.services;


import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.security.UserRepository;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.dtos.user.UserOutputDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.utils.RandomStringGenerator;
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
//    private final ImageRepository imageRepository;

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

//    public List<UserOutputDto> getUsers() {
//        List<UserOutputDto> collection = new ArrayList<>();
//        List<User> list = userRepository.findAll();
//        for (User user : list) {
//            if (user.getImage() != null) {
//                Image image = imageRepository.findById(user.getImage().getId()).orElse(null);
//                user.setImage(image);
//            }
//            collection.add(toUserOutputDto(user));
//        }
//        return collection;
//    }

    public UserOutputDto getOneUser(String username) {
        UserOutputDto dto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            dto = toUserOutputDto(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

//    public UserOutputDto getOneUser(String username) {
//        UserOutputDto dto;
//        Optional<User> user = userRepository.findById(username);
//        if (user.isPresent()) {
//            User foundUser = user.get();
//            if (foundUser.getImage() != null) {
//                Image image = imageRepository.findById(foundUser.getImage().getId()).orElse(null);
//                foundUser.setImage(image);
//            }
//            dto = toUserOutputDto(foundUser);
//        } else {
//            throw new UsernameNotFoundException(username);
//        }
//        return dto;
//    }

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
        if (userInputDto.getPassword() == null || userInputDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userInputDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userInputDto));
        newUser.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        userRepository.save(newUser);
        return newUser.getUsername();
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

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public Set<Authority> getUserAuthorities(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = fromUser(user);
            return userDto.getAuthorities();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public UserDto addUserAuthority(String username, String authority) {
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
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

//    public void assignImageToUser(String fileName, String username) {
//        Optional<User> userOptional = userRepository.findById(username);
//        Optional<ImageOutputDto> imageOptional = imageRepository.findByFileName(fileName);
//        if(userOptional.isPresent() && imageOptional.isPresent()) {
//            ImageOutputDto imageOutputDto = imageOptional.get();
//            User user = userOptional.get();
//            // Convert ImageOutputDto to Image
//            Image image = new Image();
////            image.setId(imageOutputDto.getId());
//            image.setFileName(imageOutputDto.getFileName());
//            image.setUrl(imageOutputDto.getUrl());
//            user.setImage(image);
//            userRepository.save(user);
//        } else {
//            throw new RecordNotFoundException("Er is geen user gevonden met username: " + username + " of er is geen image gevonden met filename: " + fileName);
//        }
//    }

//    public void assignImageToUser(String fileName, String username) {
//        Optional<User> userOptional = userRepository.findById(username);
//        Optional<Image> imageOptional = imageRepository.findByFileName(fileName);
//        if (userOptional.isPresent() && imageOptional.isPresent()) {
//            Image image = imageOptional.get();
//            User user = userOptional.get();
//            user.setImage(image);
//            userRepository.save(user);
//        } else {
//            throw new RecordNotFoundException("Er is geen user gevonden met username: " + username + " of er is geen image gevonden met filename: " + fileName);
//        }
//    }

    public static UserDto fromUser(User user) {
        var dto = new UserDto();
        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();
        dto.name = user.getName();
        dto.gender = user.getGender();
        dto.dob = user.getDob();
        dto.autismDiagnoses = user.getAutismDiagnoses();
        dto.autismDiagnosesYear = user.getAutismDiagnosesYear();

        return dto;
    }

    public User toUser(UserInputDto userInputDto) {
        var user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
//        user.setEnabled(userInputDto.getEnabled());
        user.setApikey(userInputDto.getApikey());
        user.setEmail(userInputDto.getEmail());
        user.setName(userInputDto.getName());
        user.setGender(userInputDto.getGender());
        user.setDob(userInputDto.getDob());
        user.setAutismDiagnoses(userInputDto.getAutismDiagnoses());
        user.setAutismDiagnosesYear(userInputDto.getAutismDiagnosesYear());

        return user;
    }

    public static UserOutputDto toUserOutputDto(User user) {
        var dto = new UserOutputDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setGender(user.getGender());
        dto.setDob(user.getDob());
        dto.setAutismDiagnoses(user.getAutismDiagnoses());
        dto.setAutismDiagnosesYear(user.getAutismDiagnosesYear());

        return dto;
    }

}
