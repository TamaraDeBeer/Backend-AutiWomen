package com.autiwomen.auti_women.security.dtos.user;

public class UserOutputDto {

    private String username;
    private String email;

    public UserOutputDto() {
    }

    public UserOutputDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
