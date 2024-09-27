package com.autiwomen.auti_women.security.controllers;

import com.autiwomen.auti_women.exceptions.BadRequestException;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.dtos.user.UserOutputDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.models.User;
import com.autiwomen.auti_women.security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserOutputDto>> getUsers() {
        List<UserOutputDto> userOutputDtos = userService.getUsers();
        return ResponseEntity.ok().body(userOutputDtos);
    }

    @GetMapping(value = "/users/{username}")
    public ResponseEntity<UserOutputDto> getOneUser(@PathVariable("username") String username) {
        UserOutputDto optionalUser = userService.getOneUser(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestPart("user") UserInputDto userInputDto,
                                                    @RequestPart(value = "file", required = false) MultipartFile file) throws IOException, IOException {
        User user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
        user.setEmail(userInputDto.getEmail());
        user.setName(userInputDto.getName());
        user.setGender(userInputDto.getGender());
        user.setDob(userInputDto.getDob());
        user.setAutismDiagnoses(userInputDto.getAutismDiagnoses());
        user.setAutismDiagnosesYear(userInputDto.getAutismDiagnosesYear());
        user.addAuthority(new Authority(user.getUsername(), "ROLE_USER"));

        String newUsername = userService.createUserWithImage(user, file);

        UserOutputDto outputDto = new UserOutputDto();
        outputDto.setUsername(newUsername);
        outputDto.setEmail(userInputDto.getEmail());
        outputDto.setProfilePictureUrl(user.getProfilePictureUrl());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).body(outputDto);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDto> updatePasswordUser(@PathVariable("username") String username, @RequestBody UserDto dto) {
        userService.updatePasswordUser(username, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUserAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addUserAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeUserAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}





