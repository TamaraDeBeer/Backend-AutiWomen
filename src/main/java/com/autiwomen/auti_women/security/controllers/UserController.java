package com.autiwomen.auti_women.security.controllers;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.dtos.user.UserOutputDto;
import com.autiwomen.auti_women.security.dtos.user.UserUpdateDto;
import com.autiwomen.auti_women.security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

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

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestPart("user") UserInputDto userInputDto,
                                                    @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        String newUsername = userService.createUserWithImage(userInputDto, file);

        UserOutputDto outputDto = new UserOutputDto();
        outputDto.setUsername(newUsername);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(uri).body(outputDto);
    }

    @PutMapping(value = "/users/{username}/password")
    public ResponseEntity<UserDto> updatePasswordUser(@PathVariable("username") String username, @RequestBody UserDto dto) {
        userService.updatePasswordUser(username, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/users/{username}/profile-picture", consumes = "multipart/form-data")
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

    @GetMapping(value = "/users/{username}/image")
    public ResponseEntity<UserOutputDto> getUserImage(@PathVariable("username") String username) {
        UserOutputDto userImageDto = userService.getUserImage(username);
        return ResponseEntity.ok().body(userImageDto);
    }

}





