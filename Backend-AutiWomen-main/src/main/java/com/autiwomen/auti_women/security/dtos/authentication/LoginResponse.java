package com.autiwomen.auti_women.security.dtos.authentication;

public class LoginResponse {

    private String jwt;

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}