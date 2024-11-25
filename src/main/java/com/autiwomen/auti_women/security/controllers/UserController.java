package com.autiwomen.auti_women.security.controllers;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.dtos.user.UserOutputDto;
import com.autiwomen.auti_women.security.dtos.user.UserUpdateDto;
import com.autiwomen.auti_women.security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getUsers() {
        List<UserOutputDto> userOutputDtos = userService.getUsers();
        return ResponseEntity.ok().body(userOutputDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> getOneUser(@PathVariable("username") String username) {
        UserOutputDto optionalUser = userService.getOneUser(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @GetMapping(value = "/{username}/image")
    public ResponseEntity<UserOutputDto> getUserImage(@PathVariable("username") String username) {
        UserOutputDto userImageDto = userService.getUserImage(username);
        return ResponseEntity.ok().body(userImageDto);
    }

    @PutMapping(value = "/{username}/password")
    public ResponseEntity<UserDto> updatePasswordUser(@PathVariable("username") String username, @RequestBody UserDto dto) {
        userService.updatePasswordUser(username, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{username}/profile-picture", consumes = "multipart/form-data")
    public ResponseEntity<Void> updateProfilePicture(@PathVariable("username") String username,
                                                     @RequestPart("file") MultipartFile file) throws IOException {
        userService.updateProfilePicture(username, file);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{username}/profile-data")
    public ResponseEntity<Void> updateUserData(@PathVariable("username") String username,
                                               @RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.updateUserData(username, userUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}/profile-picture")
    public ResponseEntity<Void> removeProfilePicture(@PathVariable("username") String username) {
        userService.removeProfilePicture(username);
        return ResponseEntity.noContent().build();
    }

}





