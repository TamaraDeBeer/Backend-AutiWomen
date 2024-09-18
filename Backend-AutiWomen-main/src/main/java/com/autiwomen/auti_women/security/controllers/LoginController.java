package com.autiwomen.auti_women.security.controllers;

import com.autiwomen.auti_women.security.dtos.authentication.LoginRequest;
import com.autiwomen.auti_women.security.dtos.authentication.LoginResponse;
import com.autiwomen.auti_women.security.services.CustomUserDetailsService;
import com.autiwomen.auti_women.security.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final JwtUtil jwtUtl;

    public LoginController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtl) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtl = jwtUtl;
    }

//    private String hashPassword(String password) {
//        return new BCryptPasswordEncoder().encode(password);
//    }
//
//    @GetMapping(value = "/authenticated")
//    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
//        return ResponseEntity.ok().body(principal);
//    }
//
//    @PostMapping(value = "/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
//
//        String username = loginRequest.getUsername();
//        String password = hashPassword(loginRequest.getPassword());
//
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//        }
//        catch (BadCredentialsException ex) {
//            throw new Exception("Incorrect username or password", ex);
//        }
//
//        final UserDetails userDetails = userDetailsService
//                .loadUserByUsername(username);
//
//        final String jwt = jwtUtl.generateToken(userDetails);
//
//        return ResponseEntity.ok(new LoginResponse(jwt));
//    }

    @PostMapping("/login")
    public ResponseEntity<?> createAccessToken(@RequestBody LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Username or password incorrect.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        final String jwt = jwtUtl.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(jwt));
    }

}
