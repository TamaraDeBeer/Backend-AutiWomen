package com.autiwomen.auti_women.dtos.images;

import com.autiwomen.auti_women.models.Image;

public class ImageOutputDto {

    String fileName;
    String contentType;
    String url;

    public ImageOutputDto() {
    }

    public ImageOutputDto(String fileName, String contentType, String url) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
