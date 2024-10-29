package com.autiwomen.auti_women.security.controllers;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import com.autiwomen.auti_women.security.models.Authority;
import com.autiwomen.auti_women.security.services.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping
    public ResponseEntity<List<Authority>> getAllAuthorities() {
        List<Authority> authorities = authorityService.getAllAuthorities();
        return ResponseEntity.ok().body(authorities);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(authorityService.getUserAuthorities(username));
    }

    @PostMapping("/{username}")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        String authorityName = (String) fields.get("authority");
        authorityService.addUserAuthority(username, authorityName);
        return ResponseEntity.ok().body(authorityService.getUserAuthorities(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> updateUserAuthority(@PathVariable("username") String username, @RequestBody UserDto userDto) {
        authorityService.updateUserAuthority(username, userDto.getOldAuthority(), userDto.getNewAuthority());
        return ResponseEntity.ok().body(authorityService.getUserAuthorities(username));
    }

    @DeleteMapping("/{username}/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        authorityService.removeUserAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}