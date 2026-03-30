package com.huong.workingsystem.serviceImpl;

import com.cloudinary.Cloudinary;
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
    public List<String> uploadFile(MultipartFile[] files, String folder) throws IOException {
        List<String> fileUrls = new ArrayList<>();
        Map<String, Object> options = ObjectUtils.asMap("folder", folder);

        for (MultipartFile file : files) {
            Map<String, Object> fileInfo = cloudinary.uploader().upload(file.getBytes(), options);
            // khi lưu image vào cloudinary , nó sẽ trả về 1 Map chứa nhiều thông tin quan trọng như assetId, public_id,width, height,...
            // secure_url là đường dẫn trực tiếp để lấy về hình ảnh đó từ Cloudinary
            String imageUrl = (String) fileInfo.get("public_id");
            fileUrls.add(imageUrl);
            System.out.println("Uploaded file URL: " + imageUrl);
        }
        return fileUrls;
    }

    @Override
    public String uploadVideo(MultipartFile file, String folder) {
        return "";
    }

    @Override
    public String getImageUrl(String publicId) {
        //publicId là id dùng để truy cập vào lấy được hình ảnh từ cloudinary
        // sau khi generate các tham số sẽ trả về link ảnh https://res.cloudinary.com/demo/image/upload/v1/avatars/user_123.jpg
        return cloudinary.url().resourceType("image").publicId(publicId).generate();
    }
}
