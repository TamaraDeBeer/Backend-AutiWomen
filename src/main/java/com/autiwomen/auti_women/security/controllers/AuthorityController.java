package com.autiwomen.auti_women.security.controllers;

import com.autiwomen.auti_women.exceptions.BadRequestException;
import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/authorities")
public class AuthorityController {

    private final UserService userService;

    public AuthorityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Authority>> getAllAuthorities() {
        List<Authority> authorities = userService.getAllAuthorities();
        return ResponseEntity.ok().body(authorities);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getUserAuthorities(username));
    }

    @PostMapping("/{username}")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addUserAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUserAuthority(@PathVariable("username") String username, @RequestBody UserDto userDto) {
        userService.updateUserAuthority(username, userDto.getOldAuthority(), userDto.getNewAuthority());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeUserAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}