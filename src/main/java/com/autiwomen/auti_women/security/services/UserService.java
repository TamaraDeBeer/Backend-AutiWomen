package com.autiwomen.auti_women.security.services;

import com.autiwomen.auti_women.exceptions.BadRequestException;
import com.autiwomen.auti_women.exceptions.RecordNotFoundException;
import com.autiwomen.auti_women.repositories.ReviewRepository;
import com.autiwomen.auti_women.security.dtos.user.UserUpdateDto;
import com.autiwomen.auti_women.security.repositories.AuthorityRepository;
import com.autiwomen.auti_women.security.repositories.UserRepository;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.dtos.user.UserOutputDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.utils.RandomStringGenerator;
import com.autiwomen.auti_women.services.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final AuthorityRepository authorityRepository;
    private final ReviewRepository reviewRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${my.upload_location}")
    private String uploadLocation;

    public UserService(UserRepository userRepository, ProfileService profileService, AuthorityRepository authorityRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.profileService = profileService;
        this.authorityRepository = authorityRepository;
        this.reviewRepository = reviewRepository;
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
        UserOutputDto dto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            dto = toUserOutputDto(user.get());
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
        return dto;
    }

    public void updatePasswordUser(String username, UserDto updateUser) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(updateUser.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
                userRepository.save(user);
                fromUser(user);
            } else {
                throw new BadRequestException("Old password is incorrect");
            }
        } else {
            throw new RecordNotFoundException("User not found: " + username);
        }
    }

    public void updateProfilePicture(String username, MultipartFile file) throws IOException {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (file != null && !file.isEmpty()) {
                String fileName = saveImage(file, username);
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/")
                        .path(fileName)
                        .toUriString();
                user.setProfilePictureUrl(imageUrl);
                userRepository.save(user);
            }
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
    }

    public void removeProfilePicture(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfilePictureUrl(null);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
    }

    @Transactional
    public void deleteUser(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            reviewRepository.deleteByUser(user);
            authorityRepository.deleteAll(user.getAuthorities());
            userRepository.delete(user);
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
    }

    public String createUserWithImage(UserInputDto userInputDto, MultipartFile file) throws IOException {
        if (userInputDto.getPassword() == null || userInputDto.getPassword().isEmpty()) {
            throw new BadRequestException("Password cannot be null or empty");
        }
        if (userRepository.existsById(userInputDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userInputDto.setApikey(randomString);

        User newUser = toUser(userInputDto);
        newUser.setPassword(passwordEncoder.encode(userInputDto.getPassword()));

        Authority authority = new Authority(newUser.getUsername(), "ROLE_USER");
        newUser.getAuthorities().add(authority);

        if (file != null && !file.isEmpty()) {
            String fileName = saveImage(file, newUser.getUsername());
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(fileName)
                    .toUriString();
            newUser.setProfilePictureUrl(imageUrl);
        } else {
            String defaultImageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path("DefaultProfileImage.png")
                    .toUriString();
            newUser.setProfilePictureUrl(defaultImageUrl);
        }
        userRepository.save(newUser);
        authorityRepository.save(authority);
        return newUser.getUsername();
    }

    private String saveImage(MultipartFile file, String username) throws IOException {
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadLocation, fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return fileName;
    }

    public UserOutputDto getUserImage(String username) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserOutputDto dto = new UserOutputDto();
            dto.setUsername(user.getUsername());
            dto.setProfilePictureUrl(user.getProfilePictureUrl());
            return dto;
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
    }

    public void updateUserData(String username, UserUpdateDto userUpdateDto) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userUpdateDto.getEmail() != null) {
                user.setEmail(userUpdateDto.getEmail());
            }
            if (userUpdateDto.getName() != null) {
                user.setName(userUpdateDto.getName());
            }
            if (userUpdateDto.getDob() != null) {
                user.setDob(userUpdateDto.getDob());
            }
            if (userUpdateDto.getAutismDiagnoses() != null) {
                user.setAutismDiagnoses(userUpdateDto.getAutismDiagnoses());
            }
            if (userUpdateDto.getAutismDiagnosesYear() != null) {
                user.setAutismDiagnosesYear(userUpdateDto.getAutismDiagnosesYear());
            }
            if (userUpdateDto.getGender() != null) {
                user.setGender(userUpdateDto.getGender());
            }
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
        }
    }

    //    Deze methode is enkel voor de CustomUserDetailsService omdat we daar een wachtwoord nodig hebben en de UserOutputDto geen wachtwoord bevat
    public UserDto getUserEntity(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            return fromUser(user.get());
        } else {
            throw new RecordNotFoundException("User not found: "+ username);
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
        dto.name = user.getName();
        dto.gender = user.getGender();
        dto.dob = user.getDob();
        dto.autismDiagnoses = user.getAutismDiagnoses();
        dto.autismDiagnosesYear = user.getAutismDiagnosesYear();
        dto.profilePictureUrl = user.getProfilePictureUrl();

        return dto;
    }

    public User toUser(UserInputDto userInputDto) {
        var user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
        user.setApikey(userInputDto.getApikey());
        user.setEmail(userInputDto.getEmail());
        user.setName(userInputDto.getName());
        user.setGender(userInputDto.getGender());
        user.setDob(userInputDto.getDob());
        user.setAutismDiagnoses(userInputDto.getAutismDiagnoses());
        user.setAutismDiagnosesYear(userInputDto.getAutismDiagnosesYear());
        user.setProfilePictureUrl(userInputDto.getProfilePictureUrl());

        return user;
    }

    public UserOutputDto toUserOutputDto(User user) {
        var dto = new UserOutputDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setGender(user.getGender());
        dto.setDob(user.getDob());
        dto.setAutismDiagnoses(user.getAutismDiagnoses());
        dto.setAutismDiagnosesYear(user.getAutismDiagnosesYear());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setAuthorities(user.getAuthorities());

        if(user.getProfile() != null){
            dto.setProfileDto(profileService.fromProfile(user.getProfile()));
        }
        return dto;
    }

}
