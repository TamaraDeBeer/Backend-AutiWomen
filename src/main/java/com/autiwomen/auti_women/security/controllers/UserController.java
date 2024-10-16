package com.autiwomen.auti_women.security.controllers;

import com.autiwomen.auti_women.exceptions.BadRequestException;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.dtos.user.UserOutputDto;
import com.autiwomen.auti_women.security.dtos.user.UserUpdateDto;
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
                                                    @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        if (file != null) {
            System.out.println("Received file: " + file.getOriginalFilename());
        } else {
            System.out.println("No file received");
        }

        String newUsername = userService.createUserWithImage(userInputDto, file);

        UserOutputDto outputDto = new UserOutputDto();
        outputDto.setUsername(newUsername);
        outputDto.setEmail(userInputDto.getEmail());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).body(outputDto);
    }

    @PutMapping(value = "/users/{username}/password")
    public ResponseEntity<UserDto> updatePasswordUser(@PathVariable("username") String username, @RequestBody UserDto dto) {
        userService.updatePasswordUser(username, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/users/{username}/profile-picture")
    public ResponseEntity<Void> updateProfilePicture(@PathVariable("username") String username,
                                                     @RequestPart("file") MultipartFile file) throws IOException {
        userService.updateProfilePicture(username, file);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/users/{username}/profile-data")
    public ResponseEntity<Void> updateUserData(@PathVariable("username") String username,
                                               @RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.updateUserData(username, userUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/users/{username}/profile-picture")
    public ResponseEntity<Void> removeProfilePicture(@PathVariable("username") String username) {
        userService.removeProfilePicture(username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/users/{username}")
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

    @PutMapping(value = "/{username}/authorities")
    public ResponseEntity<Void> updateUserAuthority(@PathVariable("username") String username, @RequestBody UserDto userDto) {
        userService.updateUserAuthority(username, userDto.getOldAuthority(), userDto.getNewAuthority());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeUserAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/authorities")
    public ResponseEntity<List<Authority>> getAllAuthorities() {
        List<Authority> authorities = userService.getAllAuthorities();
        return ResponseEntity.ok().body(authorities);
    }

    @GetMapping(value = "/users/{username}/image")
    public ResponseEntity<UserOutputDto> getUserImage(@PathVariable("username") String username) {
        UserOutputDto userImageDto = userService.getUserImage(username);
        return ResponseEntity.ok().body(userImageDto);
    }
}





