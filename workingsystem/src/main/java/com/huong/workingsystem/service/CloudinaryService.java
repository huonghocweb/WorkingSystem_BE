package com.huong.workingsystem.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CloudinaryService {

     Map<String, Object> uploadFile(MultipartFile files, String folder)throws IOException;
     String uploadVideo(MultipartFile file, String folder);
     String getImageUrl(String publicId);
     String getRawFileUrl(String publicId);
}
