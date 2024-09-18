package com.autiwomen.auti_women.security.dtos.authentication;

//public class LoginResponse {
//
//    private String accessToken;
//
//    public LoginResponse(String accessToken) {
//        this.accessToken = accessToken;
//    }
//
//    public String getAccessToken() {
//        return accessToken;
//    }
//
//}

public record LoginResponse(String jwt) {
}
