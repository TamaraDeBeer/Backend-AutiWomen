//package com.autiwomen.auti_women.models;
//
//import com.autiwomen.auti_women.security.models.User;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToOne;
//
//@Entity
//public class Image {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    private String fileName;
//    private String contentType;
//    private String url;
//
//    @OneToOne(mappedBy = "image")
//    private User User;
//
//
//    public Image(String fileName, String contentType, String url) {
//    }
//
//    public Image(Long id, String fileName, String contentType, String url) {
//        this.id = id;
//        this.fileName = fileName;
//        this.contentType = contentType;
//        this.url = url;
//    }
//
//    public Image(Long id, String fileName, String contentType, String url, com.autiwomen.auti_women.security.models.User user) {
//        this.id = id;
//        this.fileName = fileName;
//        this.contentType = contentType;
//        this.url = url;
//        User = user;
//    }
//
//    public Image() {
//
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public String getContentType() {
//        return contentType;
//    }
//
//    public void setContentType(String contentType) {
//        this.contentType = contentType;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public User getUser() {
//        return User;
//    }
//
//    public void setUser(User user) {
//        User = user;
//    }
//}
//
