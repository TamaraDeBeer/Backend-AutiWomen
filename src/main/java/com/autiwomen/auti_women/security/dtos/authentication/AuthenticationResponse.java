package com.autiwomen.auti_women.security.dtos.authentication;


//Vorm van Dto
public class AuthenticationResponse {

    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}
