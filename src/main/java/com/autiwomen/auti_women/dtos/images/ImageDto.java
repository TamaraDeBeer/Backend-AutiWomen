package com.autiwomen.auti_women.dtos.images;

import jakarta.validation.Valid;

@Valid
public class ImageDto {

    public Long id;
    public String fileName;
    public byte[] docFile;

    public ImageDto() {
    }

    public ImageDto(Long id, String fileName, byte[] docFile) {
        this.id = id;
        this.fileName = fileName;
        this.docFile = docFile;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }

}
