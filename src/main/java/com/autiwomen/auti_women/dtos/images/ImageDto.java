package com.autiwomen.auti_women.dtos.images;

import com.autiwomen.auti_women.security.dtos.user.UserDto;
import jakarta.validation.Valid;

@Valid
public class ImageDto {

    public Long id;
    public String fileName;
    public String contentType;
    public String url;

    public UserDto userDto;

    public ImageDto() {
    }

    public ImageDto(Long id, String fileName, String contentType, String url, UserDto userDto) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.url = url;
        this.userDto = userDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
