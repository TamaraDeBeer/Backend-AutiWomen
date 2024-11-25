package com.autiwomen.auti_women.security.controllers;

import com.autiwomen.auti_women.security.dtos.user.UserInputDto;
import com.autiwomen.auti_women.security.dtos.user.UserOutputDto;
import com.autiwomen.auti_women.security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestPart("user") UserInputDto userInputDto,
                                                    @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        String newUsername = userService.createUserWithImage(userInputDto, file);

        UserOutputDto outputDto = new UserOutputDto();
        outputDto.setUsername(newUsername);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(uri).body(outputDto);
    }

}
