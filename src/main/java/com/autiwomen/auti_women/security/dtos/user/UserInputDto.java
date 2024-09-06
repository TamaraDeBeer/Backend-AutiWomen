package com.autiwomen.auti_women.security.dtos.user;

import jakarta.validation.constraints.NotEmpty;

public class UserInputDto {

    @NotEmpty
    public String username;

    @NotEmpty
    public String password;

    @NotEmpty
    public String email;

    @NotEmpty
    public String apikey;

    public boolean enabled;

    public UserInputDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserInputDto() {
    }

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }

    public @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty String email) {
        this.email = email;
    }

    public @NotEmpty String getApikey() {
        return apikey;
    }

    public void setApikey(@NotEmpty String apikey) {
        this.apikey = apikey;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
