//package com.autiwomen.auti_women.controllers;
//
//import com.autiwomen.auti_women.dtos.images.ImageOutputDto;
//import com.autiwomen.auti_women.security.services.UserService;
//import com.autiwomen.auti_women.services.ImageService;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//import java.io.IOException;
//
//@CrossOrigin
//@RestController
//public class ImageController {
//
//    private final ImageService imageService;
//    private final UserService userService;
//
//    public ImageController(ImageService imageService, UserService userService) {
//        this.imageService = imageService;
//        this.userService = userService;
//    }
//
//    @PostMapping("/{username}/upload")
//    public ImageOutputDto singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable String username){
//        String fileName = imageService.storeFile(file);
//        // next line makes url. example "http://localhost:8080/download/naam.jpg"
//        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName).toUriString();
//        String contentType = file.getContentType();
//
//        userService.assignImageToUser(fileName, username);
//        return new ImageOutputDto(fileName, contentType, url );
//    }
//
//    @GetMapping("/{username}/download/{fileName}")
//    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {
//        Resource resource = imageService.downLoadFile(fileName);
//        String mimeType;
//        try{
//            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException e) {
//            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }
//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
//    }
//}
