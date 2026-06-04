package com.huong.workingsystem.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.huong.workingsystem.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    // Cấu hình đối tượng cloudinary
    public CloudinaryServiceImpl(@Value("${cloudinary.cloud_name}") String cloudName,
                             @Value("${cloudinary.api_key}") String apiKey,
                             @Value("${cloudinary.api_secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name" ,cloudName,
                "api_key" ,apiKey,
                "api_secret" ,apiSecret
        ));
    }


    @Override
    public Map<String, Object> uploadFile(MultipartFile file, String folder) throws IOException {
        if (file != null ) {
            System.out.println("Tên file gốc (Original Filename): " + file.getOriginalFilename());
            System.out.println("Định dạng file (Content Type): " + file.getContentType());
            System.out.println("Kích thước (Size): " + file.getSize() + " bytes");
        }
        String originalFileName = file.getOriginalFilename();
        System.out.println( "origin1: " +  originalFileName);
        if (originalFileName != null && originalFileName.contains(".")) {
            originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        }
        System.out.println("origin: " + originalFileName);
        List<String> fileUrls = new ArrayList<>();
        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,
                "resource_type", "auto" ,
                "overwrite" , true
        );
            Map<String, Object> fileInfo = cloudinary.uploader().upload(file.getBytes(), options);
            // khi lưu image vào cloudinary , nó sẽ trả về 1 Map chứa nhiều thông tin quan trọng như assetId, public_id,width, height,...
            // secure_url là đường dẫn trực tiếp để lấy về hình ảnh đó từ Cloudinary

            String imageUrl = (String) fileInfo.get("public_id");
            fileUrls.add(imageUrl);
            System.out.println("fileInfo " + fileInfo);
            System.out.println("Uploaded file URL: " + imageUrl);
        return fileInfo;
    }

    @Override
    public String uploadVideo(MultipartFile file, String folder) {
        return "";
    }

    @Override
    public String getImageUrl(String publicId) {
        //publicId là id dùng để truy cập vào lấy được hình ảnh từ cloudinary
        // sau khi generate các tham số sẽ trả về link ảnh https://res.cloudinary.com/demo/image/upload/v1/avatars/user_123.jpg
        return cloudinary.url()
                .transformation(new Transformation()
                        .flags("attachment")
                )
                .resourceType("image")
                .publicId(publicId)
                .generate();
    }
    @Override
    public String getRawFileUrl(String publicId) {
        return cloudinary.url()
                .resourceType("raw") // Phải chỉ định là raw cho các file tài liệu
                .publicId(publicId)
                .generate();
    }
}
