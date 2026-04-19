package com.huong.workingsystem.mapper;

import com.huong.workingsystem.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageMapper {
    private final CloudinaryService cloudinaryService;

    @Named("toFullUrl")
    public String toFullUrl(String publicId) {
        if(publicId == null && publicId.isEmpty()) {
            return null;
        }
        return cloudinaryService.getImageUrl(publicId);
    }
}
