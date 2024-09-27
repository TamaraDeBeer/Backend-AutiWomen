package com.autiwomen.auti_women.dtos.images;

public class ImageInputDto {

    public String fileName;
    public byte[] docFile;

    public ImageInputDto() {
    }

    public ImageInputDto(String fileName, byte[] docFile) {
        this.fileName = fileName;
        this.docFile = docFile;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }
}
