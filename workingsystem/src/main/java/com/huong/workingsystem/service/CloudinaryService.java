package com.huong.workingsystem.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CloudinaryService {

     List<String> uploadFile(MultipartFile[] files, String folder)throws IOException;
     String uploadVideo(MultipartFile file, String folder);
     String getImageUrl(String publicId);

}
