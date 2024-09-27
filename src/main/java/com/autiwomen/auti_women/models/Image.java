package com.autiwomen.auti_women.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    @Lob
    private byte[] docFile;


    public Image() {
    }

    public Image(String fileName, byte[] docFile) {
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

