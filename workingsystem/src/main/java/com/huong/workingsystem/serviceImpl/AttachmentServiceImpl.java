package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.context.ActivityContextHolder;
import com.huong.workingsystem.mapper.AttachmentMapper;
import com.huong.workingsystem.model.entity.Attachment;
import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.request.AttachmentRequest;
import com.huong.workingsystem.model.response.AttachmentResponse;
import com.huong.workingsystem.repo.AttachmentRepo;
import com.huong.workingsystem.repo.CardRepo;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.service.AttachmentService;
import com.huong.workingsystem.service.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepo attachmentRepo;
    private final AttachmentMapper attachmentMapper;
    private final UserRepo userRepo;
    private final CardRepo cardRepo;
    private final CloudinaryService cloudinaryService;

    @Override
    public AttachmentResponse getAttachmentById(Integer attachmentId) {
        Attachment attachment = attachmentRepo.findById(attachmentId)
                .orElseThrow(()-> new EntityNotFoundException("Not found  attachment"));
        return attachmentMapper.convertEnToRes(attachment);
    }

    @Transactional
    @Override
    public AttachmentResponse createAttachment(Integer cardId , Integer userId, MultipartFile file) throws IOException {
        Map<String , Object> fileInfo = cloudinaryService.uploadFile(file, "attachment");
        String originalName = file.getOriginalFilename();
        Card cardById = cardRepo.findById(cardId)
                .orElseThrow(()-> new EntityNotFoundException("not found attachment "));
        Attachment attachment = Attachment.builder()
                .card(cardById)
                .user(userRepo.findById(userId)
                        .orElseThrow(()-> new EntityNotFoundException("not found user")))
                .createAt(LocalDateTime.now())
                .filePublicId(fileInfo.get("public_id").toString())
                .fileName(originalName)
                .fileType(fileInfo.get("resource_type").toString())
                .fileSize(((Number) fileInfo.get("bytes")).longValue())
                .build();
        Attachment attachmentCreated = attachmentRepo.save(attachment);
        ActivityContextHolder.put("entityId", attachmentCreated.getAttachmentId().toString());
        ActivityContextHolder.put("entityName", attachmentCreated.getFileName());
        ActivityContextHolder.put("contextName", cardById.getCardTitle());
        return  attachmentMapper.convertEnToRes(attachmentCreated);
    }

    @Override
    public AttachmentResponse deleteAttachment(Integer attachmentId) {
        Attachment attachment = attachmentRepo.findById(attachmentId)
                .orElseThrow(()-> new EntityNotFoundException("Not  found attachment"));
        attachment.setDeleteAt(LocalDateTime.now());
//        ActivityContextHolder.put("entityId", attachmentId.toString());
//        ActivityContextHolder.put("entityName", attachment.getFileName());
//        ActivityContextHolder.put("contextName", cardById.getCardTitle());
        return attachmentMapper.convertEnToRes(attachmentRepo.save(attachment));
    }
}
