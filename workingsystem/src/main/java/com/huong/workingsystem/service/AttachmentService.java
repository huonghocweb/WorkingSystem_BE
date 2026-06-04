package com.huong.workingsystem.service;

import com.huong.workingsystem.model.request.AttachmentRequest;
import com.huong.workingsystem.model.response.AttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface AttachmentService {
    AttachmentResponse getAttachmentById(Integer attachmentId);
    AttachmentResponse createAttachment(Integer cardId , Integer userId , MultipartFile file) throws IOException;
    AttachmentResponse deleteAttachment(Integer attachmentId);
}
