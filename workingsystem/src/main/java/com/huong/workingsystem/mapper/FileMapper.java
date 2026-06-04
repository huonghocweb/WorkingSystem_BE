package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Attachment;
import com.huong.workingsystem.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileMapper {
    private final CloudinaryService cloudinaryService;

    @Named("toFullImageUrl")
    public String toFullImageUrl(String publicId) {
        if(publicId == null && publicId.isEmpty()) {
            return null;
        }
        return cloudinaryService.getImageUrl(publicId);
    }

    @Named("toFullFileUrl")
    public String toFullFileUrl(Attachment attachment) {
        if (attachment.getFilePublicId() == null) {
            return null;
        }
        if (attachment.getFileType() != null && attachment.getFileType().contains("image")) {
            return cloudinaryService.getImageUrl(attachment.getFilePublicId());
        }
        return cloudinaryService.getRawFileUrl(attachment.getFilePublicId());
    }
}
