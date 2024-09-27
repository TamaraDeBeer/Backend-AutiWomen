package com.autiwomen.auti_women.dtos.profiles;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProfileInputDto {

    @NotEmpty
    @Size(min = 1, max = 2000)
    public String bio;

    public String name;
    public String date;

    public ProfileInputDto(String bio, String name, String date) {
        this.bio = bio;
        this.name = name;
        this.date = date;
    }

    public ProfileInputDto() {
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
